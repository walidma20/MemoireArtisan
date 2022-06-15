<?php
require_once "conn.php";
$sql = "select Nom_metier from metier";
if(!$conn->query($sql)){
    echo "Error in connecting to Database.";
}
else{
    $result = $conn->query($sql);
    //echo $result->num_rows." fff";
    $response=array();
    if($result->num_rows > 0){
        
        //while($row = $result->fetch_array()){
            
        //    $response['metier'] = $row['Nom_metier'];
        //}

       $outp = $result->fetch_all(MYSQLI_ASSOC);
        //foreach($response as $x) echo $x."<br>";
       $my=json_encode($outp);
        echo $my;
    }
}
?>

