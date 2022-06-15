<?php
require_once "conn.php";
    $idclient=$_POST['idclient'];
    $date= Date("Y-m-d");
    
    $sql = "SELECT  Id_service, metier.Id_metier, Nom_metier from  service , metier where service.Id_metier=metier.Id_metier and Id_client = $idclient and date='$date' and Confirmartisan=1";
    if(!$conn->query($sql)){
        echo "Error in executing query.";
    }else{
    $result = $conn->query($sql);
;
    if($result->num_rows > 0){
        
    
       $outp = $result->fetch_all(MYSQLI_ASSOC);
 
       $my=json_encode($outp);
        
    }
    else $my="[]";
    echo $my;
}

?>