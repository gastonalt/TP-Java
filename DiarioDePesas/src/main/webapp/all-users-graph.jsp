<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.TreeSet"%>
<%@ page import="com.gastonalt.diariodepesas.model.UsuarioHistorialPesoDTO"%>
<%@ page import="com.gastonalt.diariodepesas.model.HistorialPeso"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="java.time.LocalDateTime"%>
<%@ page import="java.time.temporal.ChronoUnit"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
    String tipoEjercicio = (String) request.getAttribute("tipoEjercicio");
    List<UsuarioHistorialPesoDTO> usuariosHistorial = (List<UsuarioHistorialPesoDTO>) request.getAttribute("usuariosHistorial");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    Set<LocalDateTime> fechasUnicas = new TreeSet<>();
    for (UsuarioHistorialPesoDTO usuario : usuariosHistorial) {
        for (HistorialPeso peso : usuario.getPesos()) {
            fechasUnicas.add(peso.getFecha_peso());
        }
    }

    Map<String, Map<LocalDateTime, Double>> usuarioFechaPesoMap = new HashMap<>();
    for (UsuarioHistorialPesoDTO usuario : usuariosHistorial) {
        Map<LocalDateTime, Double> fechaPesoMap = new HashMap<>();
        for (HistorialPeso peso : usuario.getPesos()) {
            fechaPesoMap.put(peso.getFecha_peso(), peso.getPeso().doubleValue());
        }
        usuarioFechaPesoMap.put(usuario.getUsuario().getUsername(), fechaPesoMap);
    }

    LocalDateTime fechaMasTemprana = fechasUnicas.isEmpty() ? null : ((TreeSet<LocalDateTime>) fechasUnicas).first();
    LocalDateTime fechaMasTardia = fechasUnicas.isEmpty() ? null : ((TreeSet<LocalDateTime>) fechasUnicas).last();

    StringBuilder usuariosHistorialJson = new StringBuilder("[");
    for (int i = 0; i < usuariosHistorial.size(); i++) {
        UsuarioHistorialPesoDTO usuarioDTO = usuariosHistorial.get(i);
        usuariosHistorialJson.append("{");
        usuariosHistorialJson.append("\"usuario\": {");
        usuariosHistorialJson.append("\"username\": \"").append(usuarioDTO.getUsuario().getUsername()).append("\"");
        usuariosHistorialJson.append("},");
        usuariosHistorialJson.append("\"pesos\": [");

        Map<LocalDateTime, Double> fechaPesoMap = usuarioFechaPesoMap.get(usuarioDTO.getUsuario().getUsername());
        Double ultimoPesoConocido = null;

        int count = 0;
        for (LocalDateTime fecha : fechasUnicas) {
            Double peso = fechaPesoMap.get(fecha);
            if (peso != null) {
                ultimoPesoConocido = peso;
            }
            usuariosHistorialJson.append("{");
            usuariosHistorialJson.append("\"fecha_peso\": \"").append(fecha.format(formatter)).append("\",");
            usuariosHistorialJson.append("\"peso\": ").append(ultimoPesoConocido);
            usuariosHistorialJson.append("}");
            if (count < fechasUnicas.size() - 1) {
                usuariosHistorialJson.append(",");
            }
            count++;
        }

        usuariosHistorialJson.append("]");
        usuariosHistorialJson.append("}");
        if (i < usuariosHistorial.size() - 1) {
            usuariosHistorialJson.append(",");
        }
    }
    usuariosHistorialJson.append("]");
%>



<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><c:out value='${title}' /> - DiarioDePesas</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <%@ include file="includes/header.jsp" %>
    <br/>
    <div class="row">
        <div class="container">
            <a href="<%=request.getContextPath()%>/graph?key=setRangoPesos" class="my-3 p-3">< Volver</a>
            <h1><c:out value='${title}' /></h1>
            <h5>Mostrando desde <c:out value='${fechaInicioStr}' /> a <c:out value='${fechaFinStr}' /></h5>
            <c:if test="${not empty usuariosHistorial}">
                <canvas id="graficoProgreso"></canvas>
                <button id="downloadPDF" class="btn btn-primary mt-3">Guardar como PDF</button>
            </c:if>
            <c:if test="${empty usuariosHistorial}">
                Aún no hay datos que mostrar. Cargá más datos para visualizar el gráfico.
            </c:if>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/chartjs-adapter-date-fns"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/1.4.1/html2canvas.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.4.0/jspdf.umd.min.js"></script>
 <script>
    var usuariosHistorial = <%= usuariosHistorialJson.toString() %>;

    var datasets = usuariosHistorial.map(function(usuario) {
        return {
            label: usuario.usuario.username,
            data: usuario.pesos.map(function(historial) {
                return {
                    x: historial.fecha_peso,
                    y: historial.peso
                };
            }),
            borderColor: '#' + Math.floor(Math.random() * 16777215).toString(16),
            fill: false,
            pointRadius: 0,
            pointHoverRadius: 7
        };
    });

    var ctx = document.getElementById('graficoProgreso').getContext('2d');
    var myChart = new Chart(ctx, {
        type: 'line',
        data: {
            datasets: datasets
        },
        options: {
            scales: {
                x: {
                    type: 'time',
                    time: {
                        unit: 'day',
                        tooltipFormat: 'yyyy-MM-dd HH:mm:ss',
                        displayFormats: {
                            day: 'yyyy-MM-dd'
                        }
                    },
                    title: {
                        display: true,
                        text: 'Fecha'
                    }
                },
                y: {
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: 'Peso'
                    }
                }
            },
            plugins: {
                tooltip: {
                    callbacks: {
                        label: function(context) {
                            var label = context.dataset.label || '';
                            if (label) {
                                label += ': ';
                            }
                            label += context.parsed.y + ' kg'; // Asegúrate de que esto refleja la unidad correcta
                            return label;
                        }
                    }
                }
            }
        }
    });
    
    document.getElementById('downloadPDF').addEventListener('click', function() {
        html2canvas(document.getElementById('graficoProgreso')).then(canvas => {
            const imgData = canvas.toDataURL('image/png');
            const { jsPDF } = window.jspdf;
            const pdf = new jsPDF({
                orientation: 'landscape',
                unit: 'px',
                format: [canvas.width, canvas.height]
            });
            pdf.addImage(imgData, 'PNG', 0, 0, canvas.width, canvas.height);
            pdf.save('graficoProgreso.pdf');
        });
    });
    </script>
</body>
</html>
