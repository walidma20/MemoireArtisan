<?php 

require_once '../includes/DbOperations.php';

$response = array(); 

if($_SERVER['REQUEST_METHOD']=='POST'){
	if(isset($_POST['username']) and isset($_POST['Password'])){
		$db = new DbOperations(); 

		if($db->userLogin($_POST['username'], $_POST['Password'])){
			$client = $db->getUserByUsername($_POST['username']);
			$response['error'] = false; 
			$response['id'] = $client['Id_client'];
			$response['Email'] = $client['Email'];
			$response['Nom_utilisateur'] = $client['Nom_utilisateur'];
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