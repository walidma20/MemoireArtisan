<?php
session_start();
   
    $servername="localhost";
    $username="root";
    $password="";
    $dbname="memoire";

    $conn=mysqli_connect ($servername,$username, $password,$dbname);
      
    $metier="SELECT * FROM metier";
    
    $listmetier=mysqli_query($conn,$metier);
  
    if(isset($_POST["inscrire"])){

      $Nom=$_POST["Nom"];
      $prenom=$_POST["prenom"];
      $Nom_artisan=$_POST["Nom_artisan"];
      $Mot_pass=$_POST["Mot_pass"];
     
      $Adresse=$_POST["Adresse"];
      $Num_tel=$_POST["Num_tel"];
      $Email=$_POST["Email"];
      $metiers=array();
      foreach($_POST["metier"] as $met)$metiers[]=$met;
      
      
      $sql="INSERT INTO artisan(Nom,prenom,Nom_artisan,Mot_pass,Adresse,Num_tel,Email)VALUES 
      ('".$Nom."','".$prenom."','".$Nom_artisan."','".$Mot_pass."','".$Adresse."','.$Num_tel.','".$Email."')";
      echo $sql; 
      mysqli_query($conn,$sql);
      $Idartisant=mysqli_insert_id($conn);

      foreach($metiers as $met){

        $sql="INSERT INTO effectue(Id_artisan,id_metier)VALUES 
      ('".$Idartisant."','".$met."')";
      echo $sql; 
      mysqli_query($conn,$sql);
      }
    }

 
?>

<link type="text/css" rel="stylesheet" href=" styles.css">
<link  type="text/css" rel="stylesheet" href="select2.min.css">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/js/select2.min.js"></script>

    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <script>
 function  check_pass(){
         
         if (document.getElementById('Mot_pass').value == document.getElementById('confirmation').value) {
             document.getElementById('submit').disabled = false;
         } else {
             document.getElementById('submit').disabled = true;
             alert("mot de passe non identique");
         }
     }


        $(document).ready(
           
            function(){
            $(".mul-select").select2({
                    placeholder: "select metier", //placeholder
                    tags: true,
                    tokenSeparators: ['/',',',';'," "]
                });
            }

)
    </script>

<title>Artisans</title>
</head>
<body>
 
<center>

<fieldset>

<img src="Lgoo.png" alt="aaaa" align="center"> 

<h1>inscriver-vous!</h1>
<hr>
<form name="form1" action="site.php" method="post">
<table>
<tr>
<td>nom:        </td><td><input type="text" name="Nom" value="" autofocus required><br></br></td>
</tr>
<tr>
<td>prenom :</td> <td><input type="text" name="prenom" value="" required><br></br></td>
</tr>
<tr>
<td>nom utilisateur:        </td><td><input type="text" name="Nom_artisan" value="" required><br></br></td>
</tr>
<tr>
<td>mot de passe:        </td><td><input type="password" name="Mot_pass"value="" id="Mot_pass" onchange="check_pass();" required><br></br></td>
</tr>
<td>confirmation :        </td><td><input type="password" name="confirmation"value="" id="confirmation" onchange="check_pass();" required><br></br></td>
</tr>
<tr>
<td>address : </td><td><input type="text" name="Adresse" value="" required><br></br></td>
</tr>

<tr>
<td>
N°telephone :  </td><td><input type="tel" name="Num_tel" value="" pattern="0[567]{1}[0-9]{8}" required  ><br></br></td>
</tr>
<tr>
<td>Email :        </td><td><input type="email" name="Email" value="" required></td>
</tr>

<tr>
<td>metier:        </td><td>

    <div class="container-fluid h-100 bg-light text-dark">

        <br>
        <div class="row justify-content-center align-items-center h-100">
            <div class="col col-sm-6 col-md-6 col-lg-4 col-xl-3">
                <div class="form-group">
                    <select class="mul-select" multiple="true" name="metier[]" required>
                       <?php  while ($row = mysqli_fetch_array($listmetier)): ?>
                       <option  value="<?php echo $row[0];?>"><?php echo $row[1] ?></option>
                       <?php endwhile ?>

                    </select>
                </div> 
           </div>
        </div>
    </div>
     
 
<br></br></td>
</tr>

<tr>
<td colspan="2"><input type="submit"id="submit" name="inscrire" value="inscrire" > </td>
</tr>
</table>
</form>
</fieldset>
</center>

</body>
</html>