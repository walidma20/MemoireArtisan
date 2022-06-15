<?php 

	class DbOperations{

		private $con; 

		function __construct(){

			require_once dirname(__FILE__).'/DbConnect.php';

			$db = new DbConnect();

			$this->con = $db->connect();

		}

		/*CRUD -> C -> CREATE */

		public function createUser($Nom_utilisateur, $pass, $Email,$Num_tel,$Adresse){
			if($this->isUserExist($Nom_utilisateur,$Email)){
				return 0; 
			}else{
				$Mot_pass = $pass;
				$stmt = $this->con->prepare("INSERT INTO `client` (`Nom_utilisateur`, `Mot_pass`, `Email`, `Num_tel`, `Adresse`) VALUES (?, ?, ?, ?, ?);");
				$stmt->bind_param("sssss",$Nom_utilisateur,$Mot_pass,$Email,$Num_tel,$Adresse);			
           

				if($stmt->execute()){
					return 1; 
				}else{
					return 2; 
				}
			}
		}

		public function userLogin($Nom_utilisateur, $pass){
			$Mot_pass = $pass;
			$stmt = $this->con->prepare("SELECT Id_client FROM client WHERE Nom_utilisateur = ? AND Mot_pass = ? ");
			$stmt->bind_param("ss",$Nom_utilisateur,$Mot_pass);
			$stmt->execute();
			$stmt->store_result(); 
			return $stmt->num_rows > 0; 
		}


		public function artisanLogin($Nom_utilisateur, $pass){
			$Mot_pass = $pass;
			$stmt = $this->con->prepare("SELECT Id_artisan FROM artisan WHERE Nom_artisan = ? AND Mot_pass = ? ");
			$stmt->bind_param("ss",$Nom_utilisateur,$Mot_pass);
			$stmt->execute();
			$stmt->store_result(); 
			return $stmt->num_rows > 0; 
		}
		public function getUserByUsername($Nom_utilisateur){
			$stmt = $this->con->prepare("SELECT * FROM client WHERE Nom_utilisateur = ? ");
			$stmt->bind_param("s",$Nom_utilisateur);
			$stmt->execute();
			return $stmt->get_result()->fetch_assoc();
		}
		
		public function getArtisanByUsername($Nom_utilisateur){
			$stmt = $this->con->prepare("SELECT * FROM artisan WHERE Nom_artisan = ? ");
			$stmt->bind_param("s",$Nom_utilisateur);
			$stmt->execute();
			return $stmt->get_result()->fetch_assoc();
		}

		private function isUserExist($Nom_utilisateur, $Email){
			$stmt = $this->con->prepare("SELECT Id_client FROM client WHERE Nom_utilisateur = ? OR Email = ? ");
			$stmt->bind_param("ss", $Nom_utilisateur, $Email);
			$stmt->execute(); 
			$stmt->store_result(); 
			return $stmt->num_rows > 0; 
		}

	}