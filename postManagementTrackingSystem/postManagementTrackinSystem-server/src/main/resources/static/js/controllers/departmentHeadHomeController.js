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
	var  getNotStartedApplicationDetails = function()
	{
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
	};
	getNotStartedApplicationDetails();
	
	
	
	
	
	
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
				name: 'status',
				displayName: 'Status',
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
				cellTemplate: '<button type="button" class="btn btn-success assignOwnerButton" ng-click="grid.appScope.assignOwner(row)">Assign</button>'
			}

		]

	};
	
	
	
	/*This method assigns the owner to the NOT Started Applications and sets the status as ASSIGNED*/
	
	$scope.assignOwner = function(row)
	{
		
		if(row.entity.updateOwner===undefined || row.entity.updateOwner===null)
			{
				$scope.errorMessage="";
				$scope.errorMessage="Please assign owner to the application";
			}
		if(row.entity.eta===undefined || row.entity.eta===null)
			{
				$scope.errorMessage="";
				$scope.errorMessage="Please assign ETA to the application";
			}

	
		else
			{
			
			if(row.entity.documentRemarks===undefined||row.entity.documentRemarks===null)
			{
				row.entity.documentRemarks="";
			}
			
			var fd = new FormData();
			fd.append("applicationId",row.entity.applicationId);
			fd.append("senderName",row.entity.senderName);
			fd.append("subject",row.entity.subject);
			fd.append("priority",row.entity.priority);
			fd.append("assignedTo",row.entity.updateOwner);
			fd.append("eta",row.entity.eta);
			fd.append("documentRemarks",row.entity.documentRemarks);
			fd.append("documentPath",row.entity.documentPath);
			fd.append("status",row.entity.status);
			fd.append("documentType",row.entity.documentType);
			var url = "/assignOwner";
			
			$http({
				method: 'POST',
				url: url,
				data: fd,
				headers: {
					'Content-Type': undefined
				},
				transformRequest: angular.identity,
				transformResponse: [function (data) {
					thisIsResponse = data;
					return data;

				}]
			}).then(function (response)
						{
					
						if(response.data<0)
							{
								$scope.successMessage="";
								$scope.errorMessage="";
								$scope.errorMessage="Application could not be assigned to owner, Please contact your administrator";
							}
						else
							{
								$scope.errorMessage="";
								$scope.successMessage="Application "+row.entity.applicationId+" assigned successfully";
								getNotStartedApplicationDetails();		
								getAssignedApplications();
								populateAuditTable();
								
							}
				});
			 
			}
		
		$timeout(function () {
			$scope.errorMessage = "";
			$scope.successMessage="";
		}, 6000);
	};
	

	/*This method displays the assigned applications*/
	
	
	
	var getAssignedApplications = function()
	{
		var getAssignedApplications = "/getAssignedApplicationsDH";
		$http.get(getAssignedApplications)
		.then(function (response)
			{
		
			if(response.data==null || response.data=="")
				{
					$scope.assignedApplications=[];
				}
			else
				{
					$scope.assignedApplications.data = response.data;
				}
			});
	};
		getAssignedApplications();
	
	
	
	$scope.assignedApplications = {

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
					name: 'priority',
					displayName: 'Priority',
					width:'10%'
				},
				
				{
					name: 'status',
					displayName: 'Status',
					width:'10%',
				},
				{
					name:'name',
					displayName: 'Owner',
					width:'10%'
				},
				{
					name: 'updatedStatus',
					displayName: 'Update Status',
					width:'15%',
					cellTemplate: 'partials/showStatusDropDown.html'
				},
				
				{
					name: 'dateAssigned',
					displayName: 'Date Assigned',
					width:'10%'
				},
				
				{
					name: 'eta',
					displayName: 'ETA',
					width:'10%'
				},
				
				
				{
					name:'documentType',
					displayName: 'Document Type',
					width:'10%'
				},

				{
					name:'documentRemarks',
					displayName: 'Document Remarks',
					width:'20%'
				},
				{
					name: 'documentPath',
					displayName: 'Document',
					width: '10%',
					cellTemplate: '<img src="images/pdf.png"  ng-click="grid.appScope.downloadPdf(row.entity.documentPath,row.entity.applicationId)" class="pdfIcon" alt="PDF Icon"  class="pointer">'
				}
				

			]

		};
	
	
	/** This method shows the in action applications **/
	
	var inActionApplications = function()
	{
		var getInActionApplications = "/getInActionApplications";
		$http.get(getInActionApplications)
		.then(function (response)
			{
		
			if(response.data==null || response.data=="")
				{
					$scope.inActionApplications=[];
				}
			else
				{
					$scope.inActionApplications.data = response.data;
				}
			});
	};
	
	inActionApplications();
	
	$scope.inActionApplications = {

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
					name: 'priority',
					displayName: 'Priority',
					width:'10%'
				},
				
				{
					name: 'status',
					displayName: 'Status',
					width:'10%',
				},
				
				{
					name: 'updatedStatus',
					displayName: 'Update Status',
					width:'15%',
					cellTemplate: 'partials/showStatusDropDown.html'
				},
				
				{
					name: 'name',
					displayName: 'Owner',
					width:'15%'
				},
				
				{
					name: 'dateAssigned',
					displayName: 'Date Assigned',
					width:'10%'
				},
				
				{
					name: 'eta',
					displayName: 'ETA',
					width:'10%'
				},
				
				
				{
					name:'documentType',
					displayName: 'Document Type',
					width:'10%'
				},

				{
					name:'documentRemarks',
					displayName: 'Document Remarks',
					width:'20%'
				},
				{
					name: 'documentPath',
					displayName: 'Document',
					width: '10%',
					cellTemplate: '<img src="images/pdf.png"  ng-click="grid.appScope.downloadPdf(row.entity.documentPath,row.entity.applicationId)" class="pdfIcon" alt="PDF Icon"  class="pointer">'
				}
				

			]

		};
	

/** This method shows the On Hold Applications **/
	
	
	var  getOnHoldApplicationDetails = function()
	{
		var getOnHoldApplicationDetails = "/getOnHoldApplications";
		$http.get(getOnHoldApplicationDetails)
		.then(function (response)
				{
			
				if(response.data==null || response.data=="")
					{
						$scope.onHoldApplications=[];
					}
				else
					{
						$scope.onHoldApplications.data = response.data;
					}
		});
	};
	getOnHoldApplicationDetails();
	
	$scope.onHoldApplications = {

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
					name: 'priority',
					displayName: 'Priority',
					width:'10%'
				},
				
				{
					name: 'status',
					displayName: 'Status',
					width:'10%',
				},
				
				{
					name: 'updatedStatus',
					displayName: 'Update Status',
					width:'15%',
					cellTemplate: 'partials/showStatusDropDown.html'
				},
				
				{
					name: 'name',
					displayName: 'Owner',
					width:'15%'
				},
				
				{
					name: 'dateAssigned',
					displayName: 'Date Assigned',
					width:'10%'
				},
				
				{
					name: 'eta',
					displayName: 'ETA',
					width:'10%'
				},
				
				
				{
					name:'documentType',
					displayName: 'Document Type',
					width:'10%'
				},

				{
					name:'documentRemarks',
					displayName: 'Document Remarks',
					width:'20%'
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
					$scope.downloadError = "File generated is not a valid PDF file";
				}

			}, function (error) {
				$scope.downloadError="";
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
	
	
	
	
	
	/**This UI Grid shows the Audit Table**/
	var populateAuditTable = function()
	{
		var populateAuditTable = "/populateAuditTableDepartmentHead";
		$http.get(populateAuditTable)
		.then(function (response)
			{
		
			if(response.data==null || response.data=="")
				{
					$scope.auditTable=[];
				}
			else
				{
					$scope.auditTable.data = response.data;
				}
			});
	};
	
	populateAuditTable();
	
	$scope.auditTable = {

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
					name: 'priority',
					displayName: 'Priority',
					width:'10%'
				},
				
				{
					name: 'documentType',
					displayName: 'Document Type',
					width:'10%',
				},
				
				{
					name: 'assignedTo',
					displayName: 'Assigned To',
					width:'18%'
				},
				{
					name:'status',
					displayName: 'Updated Status',
					width:'10%'
					
				},
				
				{
					name: 'eta',
					displayName: 'ETA',
					width:'10%'
				},
				
				{
					name:'documentRemarks',
					displayName: 'Document Remarks',
					width:'20%'
				},
				{
					name: 'documentPath',
					displayName: 'Document',
					width: '10%',
					cellTemplate: '<img src="images/pdf.png"  ng-click="grid.appScope.downloadPdf(row.entity.documentPath,row.entity.applicationId)" class="pdfIcon" alt="PDF Icon"  class="pointer">'
				}
				

			]

		};
	
	
	
});