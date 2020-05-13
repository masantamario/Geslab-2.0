<%@ page language="java" contentType="text/html;" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		
		<!-- Bootstrap CSS -->
<!--		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">-->
		<link rel="stylesheet" href="../css/bootstrap.min.css">
		
		<!-- Custom CSS -->
		<link rel="stylesheet" href="../css/style.css">
		
		<title>Geslab 2.0</title>
	</head>
	
	<body>
        <div  class="container-fluid-login">
            <div class="container">
                <div class="row">
                    <div class="col-12 py-5">
                        <img class="logo-img" src="../images/logo-geslab-login.svg"> 
                    </div>
                </div>

                <div class="row align-items-center py-4">
                    <div class="col-12 col-md-5 ubu">
                        <img class="ubu-img" src="../images/logo-ubu.svg"> 
                        <p class="col-sm-12 col-lg-8 descripcion-txt mt-3 pl-0">Aplicación de gestión del inventario de los laboratorios de química desarrollada para la Universidad de Burgos</p>
                    </div>
                    
                    <div class="col-12 col-md-7">
                        <div class="row">
                            <div class="col-12 col-lg-8 login-container py-3 px-4">
                                <form action="/login.do" method="post">
                                    <div class="row align-items-center">
                                        <label for="usuario-ip" class="col-4 login-label">Usuario</label>
                                        <div class="col-8">
                                          <input type="text" class="col-12 login-input" name="usuario" id="usuario-ip" placeholder="bioqbm">
                                        </div>
                                    </div>
                                    
                                    <div class="row align-items-center mt-2">
                                        <label for="password-ip" class="col-6 login-label">Contraseña</label>
                                        <div class="col-6">
                                          <input type="password" class="col-12 login-input" name="password" id="password-ip" placeholder="********">
                                        </div>
                                    </div>
                                    
                                    <div class="row justify-content-end mt-4 px-3">
                                        <button type="submit" class="btn login-button py-0 px-3">Entrar</button>
                                    </div>
                                          
                                </form>                        
                            </div>
                            
                        </div>    
                    </div>
                </div>
            </div>
        </div>
	
<!-- 		<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script> -->
<!-- 		<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script> -->
<!--		<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>-->
		<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
		<script src="../js/bootstrap.min.js"></script>
	</body>

</html>

