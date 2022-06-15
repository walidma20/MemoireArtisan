<?php
require_once "conn.php";
    $idservice=$_POST['idservice'];
    $idclient=$_POST['idclient'];
    $date= Date("Y-m-d");
    $sql = "SELECT  description,client.Num_tel, location from  service , client where service.Id_client=client.Id_client and Id_service=$idservice and client.Id_client= $idclient and date='$date' ";
    if(!$conn->query($sql)){
        echo "Error in executing query.";
    }else{
    $result = $conn->query($sql);
;
    if($result->num_rows > 0){
        
    
       $outp = $result->fetch_all(MYSQLI_ASSOC);
 
       $my=json_encode($outp);
        echo $my;
    }
}

?>