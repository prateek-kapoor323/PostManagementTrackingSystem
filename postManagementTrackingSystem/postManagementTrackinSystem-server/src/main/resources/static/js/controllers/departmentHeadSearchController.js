var departmentHeadSearch = angular.module("app");

departmentHeadSearch.controller("departmentHeadSearchController", function ($scope, $http, registerPostService, $timeout) {
	
	
	var getDepartmentEmployeesName = "/getNameOfDepartmentEmployees";
	$scope.departmenEmployeesName = [];

	$http.get(getDepartmentEmployeesName)
	.then(function (response)
	{
		
		let dt = response.data;
		for (i in dt) {
			$scope.departmenEmployeesName.push(response.data[i].departmentEmployeeName);
			
		}
	});
	
	
	/* This method checks the type of search parameter and then sends the request to that respective controller */
	$scope.searchParams = {};
	$scope.getPostDetails = function()
	{
		if($scope.searchPostSwitch=='applicationId')
			{
			 if($scope.searchParams.applicationId === undefined || $scope.searchParams.applicationId === null || $scope.searchParams.applicationId === "")
				 {
				 	$scope.searchErrorMessage = "Please enter application id";
				 }
			 else
				 {
				 	$scope.searchErrorMessage = "";
				 	var url="/getApplicationDetailsUsingApplicationId?applicationId="+$scope.searchParams.applicationId;
				 	$http.get(url)
					.then(function (response) {
	
						$scope.searchParams.applicationId = "";
						if (response.data == null || response.data == "") {
							$scope.searchErrorMessage = "No Records found";
							$scope.searchPostInfo.data = [];
						}
						else
						{	
							$scope.searchErrorMessage="";
							$scope.searchPostInfo.data = response.data;
						}
	
					});
				 }
				$timeout(function () {
					$scope.searchErrorMessage = "";
				}, 4000);
			}
		
		else if($scope.searchPostSwitch=='status')
			{
				if($scope.searchParams.status===undefined||$scope.searchParams.status===null)
					{
						$scope.searchErrorMessage = "Please select the status";
					}
				else
					{
					$scope.searchErrorMessage = "";
				 	var url="/getApplicationDetailsUsingApplicationStatus?status="+$scope.searchParams.status;
				 	
				 	$http.get(url)
					.then(function (response) {
		
						$scope.searchParams.applicationId = "";
						if (response.data == null || response.data == "") {
							$scope.searchErrorMessage = "No Records found";
							$scope.searchPostInfo.data = [];
						}
						else
						{	
							$scope.searchErrorMessage="";
							$scope.searchPostInfo.data = response.data;
						}
		
					});
					}
				$timeout(function () {
					$scope.searchErrorMessage = "";
					}, 4000);

			}
		else if($scope.searchPostSwitch=='owner')
			{
				if($scope.searchParams.ownerName===undefined||$scope.searchParams.ownerName===null)
				{
					$scope.searchErrorMessage = "Please select the owner";
				}
				else
				{
					$scope.searchErrorMessage = "";
				 	var url="/getApplicationDetailsUsingApplicationOwner?ownerName="+$scope.searchParams.ownerName;
				 	$http.get(url)
					.then(function (response) {
		
						$scope.searchParams.applicationId = "";
						if (response.data == null || response.data == "") {
							$scope.searchErrorMessage = "No Records found";
							$scope.searchPostInfo.data = [];
						}
						else
						{	
							$scope.searchErrorMessage="";
							$scope.searchPostInfo.data = response.data;
						}
		
					});
					}
				$timeout(function () {
					$scope.searchErrorMessage = "";
					}, 4000);
			}
	}
	
	
	
	
	
	
	
	$scope.searchPostInfo = {

			enableGridMenus: false,
			enableSorting: false,
			enableFiltering: false,
			enableCellEdit: false,
			enableColumnMenus: false,
			enableHorizontalScrollbar: 1,
			enableVerticalScrollbar: 1,
			paginationPageSizes: [5, 10, 20, 30],
			paginationPageSize: 10,
			useExternalPagination: true,

			columnDefs: [{
					name: 'applicationId',
					displayName: 'Post Id',
					width: '15%'
				},
				{
					name: 'senderName',
					displayName: "Sender",
					width: '15%'
				},
				{
					name: 'subject',
					displayName: 'Subject',
					width: '15%'
				},
				{
					name: 'documentType',
					displayName: 'Document Type',
					width: '15%'
				},
				{
					name: 'referenceNumber',
					displayName: 'Reference Number',
					width: '15%'
				},
				{
					name: 'dateReceived',
					displayName: 'Date Received',
					width: '15%'
				},
				{
					name: 'dateAssigned',
					displayName: 'Date Assigned',
					width: '10%'
				},
				{
					name: 'eta',
					displayName: 'ETA',
					width: '10%'
				},
				
				{
					name: 'ownerName',
					displayName: 'Owner',
					width: '15%'
				},
				{
					name: 'status',
					displayName: 'Status',
					width: '15%'
				},
				
				{
					name: 'documentPath',
					displayName: 'Document',
					width: '10%',
					cellTemplate: '<img src="images/pdf.png"  ng-click="grid.appScope.downloadPdf(row.entity.documentPath,row.entity.applicationId)" class="pdfIcon" alt="PDF Icon"  class="pointer">'
				}
			]

		};


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