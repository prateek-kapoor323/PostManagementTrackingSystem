var departmentEmployeeHome = angular.module("app");

departmentEmployeeHome.controller("departmentEmployeeHomeController",function($scope, $http,$timeout){
	
	
	
	
	/* Getting the details of applications with status as Assigned*/
	var  getAssignedApplicationsDE = function()
	{
		var getAssignedApplicationsDE = "/getAssignedApplicationsDE";
		$http.get(getAssignedApplicationsDE)
		.then(function (response)
				{
			
				if(response.data==null || response.data=="")
					{
						$scope.assignedApplications.data=[];
					}
				else
					{
						$scope.assignedApplications.data = response.data;
					}
		});
	};
	getAssignedApplicationsDE();
	
	
	/* Getting the details of applications with status as IN ACTION*/
	var  getInActionApplicationsDE = function()
	{
		var getInActionApplicationsDE = "/getInActionApplicationsDE";
		$http.get(getInActionApplicationsDE)
		.then(function (response)
				{
			
				if(response.data==null || response.data=="")
					{
						$scope.inActionApplications.data=[];
					}
				else
					{
						$scope.inActionApplications.data = response.data;
					}
		});
	};
	getInActionApplicationsDE();
	
	
	/* Getting the details of applications with status as On Hold*/
	var  getOnHoldApplicationsDE = function()
	{
		var getOnHoldApplicationsDE = "/getOnHoldApplicationsDE";
		$http.get(getOnHoldApplicationsDE)
		.then(function (response)
				{
			
				if(response.data==null || response.data=="")
					{
						$scope.onHoldApplications.data=[];
					}
				else
					{
						$scope.onHoldApplications.data = response.data;
					}
		});
	};
	getOnHoldApplicationsDE();
	
	/* Getting the details of applications with status as Delayed*/
	var  getDelayedApplicationsDE = function()
	{
		var getDelayedApplicationsDE = "/getDelayedApplicationsDE";
		$http.get(getDelayedApplicationsDE)
		.then(function (response)
				{
			
				if(response.data==null || response.data=="")
					{
						$scope.delayedApplications.data=[];
					}
				else
					{
						$scope.delayedApplications.data = response.data;
					}
		});
	};
	getDelayedApplicationsDE();
	
	
	
	
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
					width:'15%'
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
					cellTemplate: 'partials/showStatusDepartmentEmployeeDropDown.html'
				},
				{
					name: 'update',
					displayName: 'Update',
					width: '10%',
					cellTemplate: '<button type="button" class="btn btn-success updateStatusButton" ng-click="grid.appScope.updateApplicationStatusAssigned(row)">Update Status</button>'
				},
				
				{
					name:'name',
					displayName: 'Owner',
					width:'10%'
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
					name:'referenceNumber',
					displayName: 'Reference Number',
					width:'15%'
				},

				{
					name:'documentRemarks',
					displayName: 'Document Remarks',
					width:'25%'
				},
				{
					name: 'documentPath',
					displayName: 'Document',
					width: '10%',
					cellTemplate: '<img src="images/pdf.png"  ng-click="grid.appScope.downloadPdf(row.entity.documentPath,row.entity.applicationId)" class="pdfIcon" alt="PDF Icon"  class="pointer">'
				}
				

			]

		};
	
	
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
				width:'15%'
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
				width:'10%'
			},
			
			{
				name: 'updatedStatus',
				displayName: 'Update Status',
				width:'15%',
				cellTemplate: 'partials/showStatusDepartmentEmployeeDropDown.html'
			},
			{
				name: 'update',
				displayName: 'Update',
				width: '10%',
				cellTemplate: '<button type="button" class="btn btn-success updateStatusButton" ng-click="grid.appScope.updateApplicationStatusInAction(row)">Update Status</button>'
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
				name:'referenceNumber',
				displayName: 'Reference Number',
				width:'15%'
			},
			
			{
				name:'documentRemarks',
				displayName: 'Document Remarks',
				width:'25%'
			},
			{
				name: 'documentPath',
				displayName: 'Document',
				width: '10%',
				cellTemplate: '<img src="images/pdf.png"  ng-click="grid.appScope.downloadPdf(row.entity.documentPath,row.entity.applicationId)" class="pdfIcon" alt="PDF Icon"  class="pointer">'
			}
			

		]

		};
	
	
	
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
					width:'10%'
				},
				
				{
					name: 'updatedStatus',
					displayName: 'Update Status',
					width:'15%',
					cellTemplate: 'partials/showStatusDepartmentEmployeeDropDown.html'
				},
				{
					name: 'update',
					displayName: 'Update',
					width: '10%',
					cellTemplate: '<button type="button" class="btn btn-success updateStatusButton" ng-click="grid.appScope.updateApplicationStatusToOnHold(row)">Update Status</button>'
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
					name:'referenceNumber',
					displayName: 'Reference Number',
					width:'15%'
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
	
	
	
	$scope.delayedApplications = {

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
					width:'10%'
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
					name:'referenceNumber',
					displayName: 'Reference Number',
					width:'15%'
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
	

	/** This method is invoked when the application in IN ACTION State is updated to some other state**/
	$scope.updateApplicationStatusInAction = function(row)
	{
		
		if(row.entity.updatedStatus===undefined||row.entity.updatedStatus===null)
		{
			$scope.InActionSuccessMessage="";
			$scope.InActionErrorMessage="Please select the status to be updated";
			
		}
		else
			{
			
			if (row.entity.referenceNumber === undefined || row.entity.referenceNumber === null)
			{
			    row.entity.referenceNumber = "";
			}
			
			var fd = new FormData();
			fd.append("applicationId",row.entity.applicationId);
			fd.append("senderName",row.entity.senderName);
			fd.append("subject",row.entity.subject);
			fd.append("priority",row.entity.priority);
			fd.append("assignedTo",row.entity.name);
			fd.append("eta",row.entity.eta);
			fd.append("documentRemarks",row.entity.documentRemarks);
			fd.append("documentPath",row.entity.documentPath);
			fd.append("documentType",row.entity.documentType);
			fd.append("referenceNumber",row.entity.referenceNumber);
			fd.append("updatedStatus",row.entity.updatedStatus);
			var url = "/updateApplicationStatusDE";
			
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
			
						if(response.data>0)
							{
								$scope.InActionErrorMessage = "";
								$scope.InActionSuccessMessage = "Application status successfully updated";
								getInActionApplicationsDE();//Refresh In Action Applications Table
								getOnHoldApplicationsDE();//Refresh On Hold Applications Table
								getDelayedApplicationsDE();//Refresh Delayed Applications Table
								getAssignedApplicationsDE();//Refresh Assigned Action Application Table

								
							}
						else
							{
								$scope.InActionSuccessMessage ="";
								$scope.InActionErrorMessage = "Application Status could not be updated";
							
							}
						});
			 
				
			
			}
		$timeout(function () {
			$scope.InActionSuccessMessage = "";
			$scope.InActionErrorMessage = "";
		}, 6000);
		
		
	}
	
	
	/** This method is invoked when the application in ON HOLD State is updated to some other state**/
	$scope.updateApplicationStatusToOnHold=function(row)
	{
		
		if(row.entity.updatedStatus===undefined||row.entity.updatedStatus===null)
		{
			$scope.onHoldSuccessMessage="";
			$scope.onHoldErrorMessage="Please select the status to be updated";
			
		}
		else
			{
			
			if (row.entity.referenceNumber === undefined || row.entity.referenceNumber === null) 
			{
			    row.entity.referenceNumber = "";
			}
		
			var fd = new FormData();
			fd.append("applicationId",row.entity.applicationId);
			fd.append("senderName",row.entity.senderName);
			fd.append("subject",row.entity.subject);
			fd.append("priority",row.entity.priority);
			fd.append("assignedTo",row.entity.name);
			fd.append("eta",row.entity.eta);
			fd.append("documentRemarks",row.entity.documentRemarks);
			fd.append("documentPath",row.entity.documentPath);
			fd.append("documentType",row.entity.documentType);
			fd.append("referenceNumber",row.entity.referenceNumber);
			fd.append("updatedStatus",row.entity.updatedStatus);
			var url = "/updateApplicationStatusDE";
			
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
			
						if(response.data>0)
							{
								$scope.onHoldErrorMessage = "";
								$scope.onHoldSuccessMessage = "Application status successfully updated";
								
								
								getInActionApplicationsDE();//Refresh In Action Applications Table
								getOnHoldApplicationsDE();//Refresh On Hold Applications Table
								getDelayedApplicationsDE();//Refresh Delayed Applications Table
								getAssignedApplicationsDE();//Refresh Assigned Action Application Table
							
							}
						else
							{
								$scope.onHoldSuccessMessage ="";
								$scope.onHoldErrorMessage = "Application Status could not be updated";
								
								
								
								
							}
				});
			 
				
			
			}
		$timeout(function () {
			$scope.onHoldSuccessMessage = "";
			$scope.onHoldErrorMessage = "";
		}, 6000);
		
		
	}

	
	/** This method is invoked when the application in ASSIGNED State is updated to some other state**/
	$scope.updateApplicationStatusAssigned=function(row)
	{
		
		if(row.entity.updatedStatus===undefined||row.entity.updatedStatus===null)
		{
			$scope.assignedSuccessMessage="";
			$scope.assignedErrorMessage="Please select the status to be updated";
			
		}
		else
			{
			
			if (row.entity.referenceNumber === undefined || row.entity.referenceNumber === null) 
			{
			    row.entity.referenceNumber = "";
			}
			var fd = new FormData();
			fd.append("applicationId",row.entity.applicationId);
			fd.append("senderName",row.entity.senderName);
			fd.append("subject",row.entity.subject);
			fd.append("priority",row.entity.priority);
			fd.append("assignedTo",row.entity.name);
			fd.append("eta",row.entity.eta);
			fd.append("documentRemarks",row.entity.documentRemarks);
			fd.append("documentPath",row.entity.documentPath);
			fd.append("documentType",row.entity.documentType);
			fd.append("referenceNumber",row.entity.referenceNumber);
			fd.append("updatedStatus",row.entity.updatedStatus);
			var url = "/updateApplicationStatusDE";
			
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
			
						if(response.data>0)
							{
								$scope.assignedErrorMessage = "";
								$scope.assignedSuccessMessage = "Application status successfully updated";
								

								getInActionApplicationsDE();//Refresh In Action Applications Table
								getOnHoldApplicationsDE();//Refresh On Hold Applications Table
								getDelayedApplicationsDE();//Refresh Delayed Applications Table
								getAssignedApplicationsDE();//Refresh Assigned Action Application Table
								
							}
						else
							{
								$scope.assignedSuccessMessage ="";
								$scope.assignedErrorMessage = "Application Status could not be updated";
								
								
							}
						});
			 
				
			
			}
		$timeout(function () {
			$scope.assignedSuccessMessage = "";
			$scope.assignedErrorMessage = "";
		}, 6000);
		
		
	}
	

	

});
