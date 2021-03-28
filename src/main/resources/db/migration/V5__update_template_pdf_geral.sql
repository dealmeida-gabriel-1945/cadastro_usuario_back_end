UPDATE template SET conteudo = '
<!DOCTYPE html>
<html>
<head>
	<title>Listagem Geral</title>
	<style>
		body{
            font-size: 12pt;
            font-family: Calibri;
            text-align: justify;
            width: 210mm;
            height: 297mm;
            line-height: 1;
        }
        .paginacao{
            padding-top: 2cm;
            padding-bottom: 2cm;
            padding-left: 3cm;
            padding-right: 1.5cm;
            width: 100%;
            height: 100%;
        }
        .text-center{
            text-align: center;
        }

        table{
        	width: 100%;
        	border-collapse: collapse;
        }
        .titulo{
        	font-weight: bold;
        	font-size: 20px;
        }
        .padding{
            padding-top: 5px;
            padding-bottom: 5px;
            padding-right: 5px;
            padding-left: 5px;
        }
        .col-10{
        	width: 100%;
        }
        .avatar{
        	border-radius: 50%;
        }
	</style>
</head>
<body>
	<h3 class="text-center">Listagem de Usuários - Develcode</h3>

	<table border="1" class="text-center">
		<thead>
			<tr>
				<td class="padding titulo">Foto</td>
				<td class="padding titulo">Nome</td>
				<td class="padding titulo">Data Nascimento</td>
			</tr>
		</thead>
		<tbody th:each="usuario: ${usuarios}">
			<tr>
				<td class="padding">
					<img width="75px" height="75px" class="avatar" th:src="${usuario.foto}" />
				</td>
				<td class="padding">
					<span th:text="${usuario.nome}"></span>
				</td>
				<td class="padding">
					<span th:text="${usuario.dataNascimento}"></span>
				</td>
			</tr>
		</tbody>
	</table>
	<h5 class="text-center">Documento expedido em: <span th:text="${dataExpedicao}"></span></h5>
</body>
</html>
' WHERE id = 1;