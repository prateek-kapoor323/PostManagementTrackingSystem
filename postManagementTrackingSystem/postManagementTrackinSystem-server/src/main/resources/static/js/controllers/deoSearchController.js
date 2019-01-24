var deoSearch = angular.module("app");

deoSearch.controller("deoSearchController", function ($scope, $http, $timeout) {

	$scope.searchParams = {};
	/*This method is used to get the details on the basis of the following parameters 1. postId(applicationId) 2.dateRange 3.department*/
	$scope.searchPostDetails = function () {
		if ($scope.searchPostSwitch == 'applicationId') {

			
			if($scope.searchParams.applicationId === undefined || $scope.searchParams.applicationId === null || $scope.searchParams.applicationId === "")
				{
					$scope.searchErrorMessage = "No Records Found";
				}
			
			else
				{
				    $scope.searchErrorMessage = "No Records Found";
					var searchUrl = '/searchApplicationById?applicationId=' + $scope.searchParams.applicationId;
		
					$http.get(searchUrl)
						.then(function (response) {
		
							$scope.searchParams.applicationId = "";
							if (response.data == null || response.data == "") {
								$scope.searchErrorMessage = "No Records found";
								$scope.searchPostInfo.data = [];
							} else {
								$scope.searchPostInfo.data = response.data;
							}
		
						});
		
					$timeout(function () {
						$scope.searchErrorMessage = "";
					}, 4000);

				}

		} else if ($scope.searchPostSwitch == 'department') {

			var searchUrl = '/searchApplicationByDepartment?department=' + $scope.searchParams.department;

			$http.get(searchUrl)
				.then(function (response) {

					$scope.searchParams.department = "";

					if (response.data == null || response.data == "") {
						$scope.searchErrorMessage = "No Records found";
						$scope.searchPostInfo.data = [];
					} else {

						$scope.searchPostInfo.data = response.data;
					}


				});

			$timeout(function () {
				$scope.searchErrorMessage = "";
			}, 4000);


		} else if ($scope.searchPostSwitch == 'date') {
			var searchUrl = '/searchApplicationByDate?from=' + $scope.searchParams.from + '&to=' + $scope.searchParams.to;

			$http.get(searchUrl)
				.then(function (response) {

					$scope.searchParams.to = "";
					$scope.searchParams.from = "";

					if (response.data == null || response.data == "") {
						$scope.searchErrorMessage = "No Records found";
						$scope.searchPostInfo.data = [];
					}
					else
					{
						$scope.searchErrorMessage = "";
						$scope.searchPostInfo.data = response.data;
					}


				});

			$timeout(function () {
				$scope.searchErrorMessage = "";
			}, 4000);

		}
	}


	/*This UI grid populates the details of post on the basis of date range, postId, department*/
	$scope.searchPostInfo = {

		enableGridMenus: false,
		enableSorting: false,
		enableFiltering: false,
		enableCellEdit: false,
		enableColumnMenus: false,
		enableHorizontalScrollbar: 0,
		enableVerticalScrollbar: 0,
		paginationPageSizes: [5, 10, 20, 30],
		paginationPageSize: 10,
		useExternalPagination: true,

		columnDefs: [{
				name: 'applicationId',
				displayName: 'Post Id'
			},
			{
				name: 'senderName',
				displayName: "Sender",
				width: '15%'
			},
			{
				name: 'dateReceived',
				displayName: 'Date Received'
			},
			{
				name: 'subject',
				displayName: 'Subject',
				width: '10%'
			},
			{
				name: 'priority',
				displayName: 'Priority'
			},
			{
				name: 'name',
				displayName: 'SCGJ POC'
			},
			{
				name: 'department',
				displayName: 'Department',
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