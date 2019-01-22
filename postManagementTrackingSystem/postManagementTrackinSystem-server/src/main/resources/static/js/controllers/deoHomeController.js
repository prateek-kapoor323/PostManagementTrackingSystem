var deoHome= angular.module("app");

deoHome.controller("deoHomeController",function($scope, $http,registerPostService,$timeout){
	
	
	
	
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
    
    
     	$scope.submitPostDetails = function()
     	{
     		
     		if($scope.postDetails.additionalComments===undefined)
     			{
     				$scope.postDetails.additionalComments="";
     			}
     		
     		var senderName = $scope.postDetails.senderName;
     		var pointOfContact = $scope.postDetails.pointOfContact;
     		var contactNumber = $scope.postDetails.contactNumber;
     		var dateReceived = $scope.postDetails.dateReceived;
     		var priority = $scope.postDetails.priority;
     		var subject = $scope.postDetails.subject;
     		var documentType = $scope.postDetails.documentType;
     		var ownerName = $scope.postDetails.ownerName;
     		var additionalComments = $scope.postDetails.additionalComments;
     		var file = $scope.postDetails.file;
     		var uploadUrl = "/submitPostDetails";
     		var registerPost = registerPostService.submitPost(senderName,pointOfContact,contactNumber,dateReceived,priority,subject,documentType,ownerName,additionalComments,file,uploadUrl);
     		
     	}
});
