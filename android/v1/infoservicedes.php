<?php
require_once "conn.php";
    $idservice=$_POST['idservice'];
 
    $date= Date("Y-m-d");
    $sql = "SELECT  description from   service  where  Id_service=$idservice  and date='$date' ";
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