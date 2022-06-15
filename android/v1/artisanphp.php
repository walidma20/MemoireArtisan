<?php
require_once "conn.php";
if(isset($_POST['metier'])){
    $Nom_metier=$_POST['metier'];
    $sql = "SELECT artisan.Id_artisan, artisan.Nom_artisan from  effectue,artisan,metier where effectue.Id_artisan=artisan.Id_artisan and metier.Id_metier=effectue.Id_metier and metier.Nom_metier='$Nom_metier'";
    if(!$conn->query($sql)){
        echo "Error in executing query.";
    }else{
    $result = $conn->query($sql);
    //echo $result->num_rows." fff";
    //$response=array();
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
}
?>

