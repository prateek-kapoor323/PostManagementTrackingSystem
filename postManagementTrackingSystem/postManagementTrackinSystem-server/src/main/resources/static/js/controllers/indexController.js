
var app = angular.module('app', ['ngRoute',
								'ui.grid',
							    'ui.grid.edit',
							    'ui.grid.cellNav',
							    'ui.grid.autoResize',
							    'ui.bootstrap',
							    'ui.grid.pagination']);




app.config(function($routeProvider, $httpProvider){
	$routeProvider.when('/DEO', {
	    templateUrl : 'dataEntryOperator.html',
	    controller : 'dataEntryOperatorController'

	})
	.when('/DH', {
        templateUrl : 'departmentHeads.html',
        controller : 'departmentHeadsController'

    })
    .when('/DE',{
    	templateUrl : 'departmentEmployees.html',
    	controller : 'departmentEmployeeController'
    })
    .when('/scgjAdmin',{
    	templateUrl : 'scgjAdmin.html',
    	controller : 'scgjAdminController'
    })
    .otherwise('/');

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

});


app.controller('mainController', function($rootScope, $scope, $http, $location, $route, $window, $timeout)  {
	$rootScope.mainTemplate=true;
	$scope.invalidErrorMessage="";
	$scope.credential={userEmail: '',password:''}
	
//	var nameOfUser = function()
//	{
//		$http.get('/getNameOfUser')
//		.then(function(response){
//			
//		});
//	}
	
	
	
	var authenticate = function(credentials){
		
		var headers = credentials? { authorization : "Basic " + btoa(credentials.userEmail + ":" + credentials.password)}:{};
	
		$http.get('/user', {headers : headers}).then(function(response){
		
			
			
			
			$rootScope.userRole = JSON.stringify(response.data.authorities[0].authority);	
			$rootScope.username = response.data.principal.username;	
			$rootScope.mainTemplate=false;
			
			if(credentials){
				
				$http.get('/getUserName').then(function(nameOfUser){
					$rootScope.nameOfuser=nameOfUser.data.trainingPartnerName;
					
					
				},function(error){
					
				});
				
			}

	// Routing between templates based on user-role
			
			if($rootScope.userRole == '"DEO"'){
				$rootScope.mainTemplate=false;
				$location.path("/DEO");
			}
			else if($rootScope.userRole == '"DH"'){
				$rootScope.mainTemplate=false;
				$location.path("/DH");
			}
			else if($rootScope.userRole == '"DE"'){
				$rootScope.mainTemplate=false;
				$location.path("/DE");
			}
			else if($rootScope.userRole == '"ADMIN"'){
				$rootScope.mainTemplate=false;
				$location.path("/scgjAdmin");
			}
			else
				$location.path("/");

		
			
		}, function(data){
			// function which executes when the user is not authenticated
			$rootScope.mainTemplate= true;
			if(credentials){
				$scope.invalidErrorMessage="Invalid Username/ Password";
			}
			else{
				$scope.invalidErrorMessage="";
			}
			$timeout(function() {
		         $scope.invalidErrorMessage="";
		      }, 3000);
		});
	}
	
	// Calling authenticate function, if user is already logged in and gave the reload command
	authenticate();
	
	$scope.login= function(){
		authenticate($scope.credential);
		
	}

	
	// function for logout action
	$scope.logout = function($route) {
        $rootScope.type = "logout"; 	
        $http.post('logout', {}).finally(function() {
        $rootScope.mainTemplate= true;
        $location.path("/");
        	
        });
	}
	
	 
	
	
});

