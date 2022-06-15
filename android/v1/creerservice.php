
<?php
require_once "conn.php";
    $artisan=$_POST['artisan'];
    $idclient=$_POST['idclient'];
    $metier=$_POST['metier'];
    $description=$_POST['description'];
    $location=$_POST['location'];
    $idmetier="";
    $idartisan="";
   
    $sql = "SELECT Id_artisan from  artisan where Nom_artisan='$artisan'";
    if(!$conn->query($sql)){
        echo "Error in executing query.";
    }else{
    $result = $conn->query($sql);
;
    if($result->num_rows > 0){
        
    
       $outp = $result->fetch_array(MYSQLI_ASSOC);
 
       $idartisan=$outp['Id_artisan'];
      // echo "artisan :".$idartisan;
        
    }
}

    $sql = "SELECT Id_metier from  metier where Nom_metier='$metier'";
    if(!$conn->query($sql)){
        echo "Error in executing query.";
    }else{
    $result = $conn->query($sql);
;
    if($result->num_rows > 0){
        
    
       $outp = $result->fetch_array(MYSQLI_ASSOC);
 
       $idmetier=$outp['Id_metier'];

        //echo "metier:".$idmetier;
    }
}

    $date=Date('Y-m-d');

    $sql =  "insert into service values('', $idmetier, $idartisan, $idclient, '$description', '$location', null,null, '$date')";
    if(!$conn->query($sql)){
        echo "Error in executing query.";
    }else{
    $result = $conn->query($sql);
    if($result === TRUE) echo "1";
    else  echo "0";
    }  
    
    

?>