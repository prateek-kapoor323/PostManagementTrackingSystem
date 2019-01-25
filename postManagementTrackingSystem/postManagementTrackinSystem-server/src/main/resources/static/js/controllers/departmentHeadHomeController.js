var departmentHeadHome = angular.module("app");

departmentHeadHome.controller("departmentHeadHomeController", function ($scope, $http, $timeout) {
	
	
	
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
	
	
	
	
	
	/* Getting the details of applications with status as not started(New Applications)*/
	var getNotStartedApplicationDetails = "/getNotStartedApplications";
	$http.get(getNotStartedApplicationDetails)
	.then(function (response)
			{
		
			if(response.data==null || response.data=="")
				{
					$scope.newApplications=[];
				}
			else
				{
					$scope.newApplications.data = response.data;
				}
	});
	
	
	
	
	
	/*This UI grid populates the details of post that are assigned to the departments with status not started*/
	$scope.newApplications = {

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
				width:'8%'
			},
			{
				name: 'senderName',
				displayName: "Sender",
				width: '25%'
			},
			
			{
				name: 'subject',
				displayName: 'Subject',
				width: '12%'
			},
			
			{
				name: 'dateReceived',
				displayName: 'Date Received',
				width:'10%'
			},
			
			{
				name: 'priority',
				displayName: 'Priority',
				width:'10%'
			},
			{
				name:'documentType',
				displayName: 'Document Type',
				width:'10%'
			},
			{
				name: 'documentPath',
				displayName: 'Document',
				width: '10%',
				cellTemplate: '<img src="images/pdf.png"  ng-click="grid.appScope.downloadPdf(row.entity.documentPath,row.entity.applicationId)" class="pdfIcon" alt="PDF Icon"  class="pointer">'
			},
			{
				name: 'updateOwner',
				displayName: 'Assign To',
				cellTemplate: 'partials/departmentEmployeesDropDown.html',
				width:'16%'
			},
			{
				name: 'eta',
				displayName: 'ETA',
				cellTemplate: 'partials/datePickerNewApplication.html',
				width:'15%'
			},
			
			{
				name:'additionalComments',
				displayName: 'Additional Comment',
				width:'30%'
			},
			{
				name:'documentRemarks',
				displayName: 'Add Remarks',
				cellTemplate:'partials/inputFormAddRemarks.html',
				width:'50%'
			},
			
			{
				name: 'assign',
				displayName: 'Update Owner',
				width: '10%',
				cellTemplate: '<button type="button" class="btn btn-success assignOwnerButton">Assign</button>'
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
					$scope.downloadError = "File generated is not a valid PDF file";
				}

			}, function (error) {
				$scope.downloadError = "An error occured while downloading file.";

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