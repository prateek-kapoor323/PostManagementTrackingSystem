var deoHome= angular.module("app");

deoHome.controller("deoHomeController",function($scope, $http){
	
	
	
	var url = '/getDepartmentHeadsName';
    $scope.departmentHeadsName = [];
    $http.get(url)
	    .then(function(response) {
	    	console.log(response.data);
	        let dt = response.data;
	        for (i in dt) {
	            $scope.departmentHeadsName.push(response.data[i].departmentHeadName);
	        }
	    });
});
