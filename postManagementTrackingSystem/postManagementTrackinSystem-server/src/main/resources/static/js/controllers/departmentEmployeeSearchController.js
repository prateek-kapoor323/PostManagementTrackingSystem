var departmentEmployeeSearch = angular.module("app");

departmentEmployeeSearch.controller("departmentEmployeeSearchController",function($scope, $http, $timeout){

	
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
				 	var url="/getApplicationDetailsByApplicationIdDE?applicationId="+$scope.searchParams.applicationId;
				 	$http.get(url)
					.then(function (response) {
	
						$scope.searchParams.applicationId = "";
						if (response.data == null || response.data == "") 
						{
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
				}, 6000);
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
				 	var url="/getApplicationDetailsUsingApplicationStatusDE?status="+$scope.searchParams.status;
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
					}, 6000);

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
					width: '25%',
					cellTooltip: function(row, col) {
				        return 'Sender: ' + row.entity.senderName;
				      }
				},
				{
					name: 'subject',
					displayName: 'Subject',
					width: '15%',
					cellTooltip: function(row, col) {
				        return 'Subject: ' + row.entity.subject;
				      }
				},
				{
					name: 'documentType',
					displayName: 'Document Type',
					width: '15%',
					cellTooltip: function(row, col) {
				        return 'Document Type: ' + row.entity.documentType;
				      }
				},
				{
					name: 'referenceNumber',
					displayName: 'Reference Number',
					width: '16%',
					cellTooltip: function(row, col) {
				        return 'Reference Number: ' + row.entity.referenceNumber;
				      }
				},
				{
					name: 'dateReceived',
					displayName: 'Date Received',
					width: '13%'
				},
				{
					name: 'dateAssigned',
					displayName: 'Date Assigned',
					width: '13%'
				},
				{
					name: 'eta',
					displayName: 'ETA',
					width: '15%'
				},
				
				{
					name: 'ownerName',
					displayName: 'Owner',
					width: '12%',
					cellTooltip: function(row, col) {
				        return 'Owner: ' + row.entity.ownerName;
				      }
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