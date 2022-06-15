<?php 

require_once '../includes/DbOperations.php';

$response = array(); 

if($_SERVER['REQUEST_METHOD']=='POST'){
	if(isset($_POST['username']) and isset($_POST['Password'])){
		$db = new DbOperations(); 

		if($db->artisanLogin($_POST['username'], $_POST['Password'])){
			$user = $db->getArtisanByUsername($_POST['username']);
			$response['error'] = false; 
			$response['id'] = $user['Id_artisan'];
			$response['Email'] = $user['Email'];
			$response['Nom_artisan'] = $user['Nom_artisan'];
		}else{
			$response['error'] = true; 
			$response['message'] = "Invalid Nom_utilisateur or Mot_pass";			
		}

	}else{
		$response['error'] = true; 
		$response['message'] = "Required fields are missing";
	}
}

echo json_encode($response);