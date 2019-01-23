var deoEdit = angular.module("app");

deoEdit.controller("deoEditController", function ($scope, $http, $timeout) {


	/*This method gets the application ids whose status is NOT STARTED*/
	var url = '/getNotStartedApplicationId';
	$scope.applicationId = [];

	$http.get(url)
		.then(function (response) {
			let dt = response.data;
			for (i in dt) {
				$scope.applicationId.push(response.data[i].applicationId);
			}
		});


	/* This method is used to get the names of department heads*/
	var departmentHeadUrl = '/getDepartmentHeadsName';
	$scope.departmentHeadsName = [];
	$http.get(departmentHeadUrl)
		.then(function (response) {
			console.log(response.data);
			let dt = response.data;
			for (i in dt) {
				$scope.departmentHeadsName.push(response.data[i].departmentHeadName);
			}
		});


	/*This method gets the details of the application whose status is NOT STARTED*/
	$scope.getPostDetail = function () {
		var searchUrl = '/searchApplicationById?applicationId=' + $scope.searchParam.applicationId;
		$http.get(searchUrl)
			.then(function (response) {
				$scope.searchParam.applicationId = "";
				console.log(response);
				if (response.data == null || response.data == "") {
					$scope.searchResultError = "No Results Found";
					$scope.editPostDetail = [];

				} else {
					$scope.editPostDetail.data = response.data;
				}
			});
		$timeout(function () {
			$scope.searchResultError = "";
		}, 4000);
	}


	/* UI Grid to populate the post details when the DEO wants to update the owner of the application */
	$scope.editPostDetail = {

		enableGridMenus: false,
		enableSorting: false,
		enableFiltering: false,
		enableCellEdit: false,
		enableColumnMenus: false,
		enableHorizontalScrollbar: 1,
		enableVerticalScrollbar: 0,
		paginationPageSizes: [5, 10, 20, 30],
		paginationPageSize: 10,
		useExternalPagination: true,

		columnDefs: [{
				name: 'applicationId',
				displayName: 'Post Id',
				width: '12%'
			},
			{
				name: 'senderName',
				displayName: "Sender",
				width: '20%'
			},
			{
				name: 'dateReceived',
				displayName: 'Date Received',
				width: '10%'
			},
			{
				name: 'subject',
				displayName: 'Subject',
				width: '20%'
			},
			{
				name: 'name',
				enableCellEdit: true,
				displayName: 'SCGJ POC',
				width: '15%'

			},
			{
				name: 'newOwner',
				enableCellEdit: true,
				cellTemplate: 'partials/departmentHeadOwnerDropdownTemplate.html',
				displayName: 'New Owner',
				width: '15%'

			},
			{
				name: 'documentPath',
				displayName: 'Document',
				width: '12%',
				cellTemplate: '<img src="images/pdf.png"  ng-click="grid.appScope.downloadPdf(row.entity.documentPath,row.entity.applicationId)" style="margin-top: 3%;width: 15%;margin-left: 45%;"alt="PDF Icon"  class="pointer">'
			},
			{
				name: 'assign',
				displayName: 'Update Owner',
				width: '15%',
				cellTemplate: '<button type="button"  class="btn btn-success" ng-click=grid.appScope.updateOwner(row.entity.newOwner,row.entity.applicationId)  style="font-size:11px;padding:2px;margin-left:18%;margin-top:3%;">Update Owner</button>'
			}
		]

	};


	/* Method to update the owner of the application with application id and owner Name, these values are received from $scope.editPostDetail UI Grid's assign column definition **/
	$scope.updateOwner = function (newOwnerName, applicationId) {
		if (newOwnerName == undefined || newOwnerName == null || newOwnerName == "") {
			$scope.mandatoryFeildsError = "Please select the new owner";
		} else if (applicationId == undefined || applicationId == null || applicationId == "") {
			$scope.mandatoryFeildsError = "Owner cannot be updated, Please contact your administrator";
		} else {
			console.log("Update Owner Here");
			var updateUrl = '/editAssignee?applicationId=' + applicationId + '&ownerName=' + newOwnerName;
			console.log("The url with params is: " + updateUrl);

			$http.get(updateUrl)
				.then(function (response) {

					console.log(response.data);
					if (response.data == 1) {
						console.log("Success");
						$scope.updateSuccessMessage = "Application Owner Updated Successfully";
					} else {
						console.log("Unsuccessfull");
						$scope.updateErrormessage = "Application Owner could not be updated";
					}


				});
			$timeout(function () {
				$scope.updateErrormessage = "";
				$scope.updateSuccessMessage = "";
			}, 4000);

		}

		$timeout(function () {
			$scope.mandatoryFeildsError = "";
		}, 4000);


	}


	/** This method takes the document path and application id to download the path by sending request to backend **/
	$scope.downloadPdf = function (documentPath, applicationId) {
		var downloadFileUrl = '/downloadFile?filePath=' + documentPath;
		$http.get(downloadFileUrl, {
				responseType: 'arraybuffer'
			})
			.then(function (response) {
				if (response.data.byteLength != 0) {
					var pdfFile = new Blob([response.data], {
						type: 'application/pdf'
					})
					var downloadURL = URL.createObjectURL(pdfFile);
					var link = document.createElement('a');
					link.href = downloadURL;
					link.download = applicationId;
					document.body.appendChild(link);
					link.click();
				} else {
					$scope.downloadError = "File generated is not a valid PDF file"
				}

			}, function (error) {
				$scope.downloadError = "An error occured while downloading file."

			});
		$timeout(function () {
			$scope.downloadError = "";
		}, 6000)

	}


	var content = 'file content for example';
	var blob = new Blob([content], {
		type: 'text/plain'
	});
	$scope.url = (window.URL || window.webkitURL).createObjectURL(blob);


});