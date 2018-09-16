<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<link href="webjars/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet">

<link href="css/style.css" rel="stylesheet">

</head>

<body>
	<!-- Fixed navbar -->
	<div class="navbar navbar-static navbar-default navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
			<button type="button" class="navbar-toggle toggle-left"
				data-toggle="sidebar" data-target=".sidebar-left">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<p class="navbar-brand">Menu</p>
			</div>

		</div>
	</div>

	<!-- Begin page content -->
	<div class="container-fluid">
		<div class="row">
			<div
				class="col-xs-7 col-sm-3 col-md-3 sidebar sidebar-left sidebar-animate">
				<ul class="nav navbar-stacked">
					<li class="active"><a href="/inicio">Home</a></li>
					<li><a href="/alta">Alquilar Cancha</a></li>
					<li><a href="/bajaModificacion">Baja/Modificacionde alquiler</a></li>
				</ul>
			</div>
			<div class="main col-md-9 col-md-offset-3">
				