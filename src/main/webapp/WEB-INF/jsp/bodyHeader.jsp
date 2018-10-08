<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  
<link href="http://localhost:8080/webjars/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet">

<link href="http://localhost:8080/css/style.css" rel="stylesheet">
<link href="http://localhost:8080/webjars/datatables/1.10.19/css/jquery.dataTables.css" rel="stylesheet">
<link href="http://localhost:8080/webjars/Eonasdan-bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.min.css" rel="stylesheet">

</head>

<body>


<div id="sideNavigation" class="sidenav">
  <a id="cerrar" href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
  <a href="/inicio">Home</a>
  <a href="/alquiler/buscarCanchas">Alquilar Cancha</a>
 <a href="/alquiler/bajaModificacionAlquiler">Baja/Modificacionde alquiler</a>
</div>
 
<nav class="topnav">
  <a href="#" onclick="openNav()">
    <svg width="30" height="30" id="icoOpen">
        <path d="M0,5 30,5" stroke="#000" stroke-width="5"/>
        <path d="M0,14 30,14" stroke="#000" stroke-width="5"/>
        <path d="M0,23 30,23" stroke="#000" stroke-width="5"/>
    </svg>
  </a>
  <a class="navbar-brand" href="#">${titulo}</a>
</nav>
 

<div id="main" class="container-fluid">
				