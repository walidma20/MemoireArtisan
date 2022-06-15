<?php
require_once "conn.php";
    $idartisant=$_POST["idartisan"];
    $date= Date("Y-m-d");
    
    $sql = "SELECT  Id_service,client.Id_client,Nom_utilisateur from  service , client where service.Id_client=client.Id_client and Id_artisan =$idartisant and date='$date' ";
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