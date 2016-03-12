angular.module("newInovaVitrine").service("faceDetectionAPI", function ($http) {
    this.getDetection = function () {
        //return $http.get("http:// url da api de face detection");
       return getRandomInt();
    };

    function getRandomInt() {
        var max = 3;
        var min = 1;

        var random = Math.floor(Math.random() * (max - min)) + min;
        console.log(random)
        return random;
    }
});