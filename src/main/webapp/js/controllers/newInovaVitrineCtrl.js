angular.module("newInovaVitrine").controller("newInovaVitrineCtrl", function ($scope,  faceDetectionAPI) {
	$scope.app = "Propagandas";

    $scope.propagandas =   "images/propagandas/propaganda-"+faceDetectionAPI.getDetection()+".jpg";

    $scope.selecionarSexo= function(sexo){
        numeroFoto=sexo;
        $scope.propagandas =   "images/propagandas/propaganda-"+numeroFoto+".jpg";
    }

});