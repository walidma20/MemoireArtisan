
<?php
require_once "conn.php";
    $service=$_POST['idservice'];
  
   
    $sql =  " update service set Confirmartisan=0 where Id_service= $service ";
    if(!$conn->query($sql)){
        echo "Error in executing query.";
    }else{
    $result = $conn->query($sql);
    if($result === TRUE) echo "1";
    else  echo "0";
    }  
    
    

?>