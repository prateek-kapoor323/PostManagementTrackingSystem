var departmentHeadEdit = angular.module("app");

departmentHeadEdit.controller("departmentHeadEditController", function ($scope, updatePostService,$http, $timeout) 
{
	
	
	
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
	

	$scope.searchParams={};
	$scope.searchEditOwner = function()
	{
		var url = "/showEditApplicationDetailsDH?applicationId="+$scope.searchParams.applicationId;
		
			$http.get(url)
			.then(function (response) {
				$scope.searchParams.applicationId = "";
				
				if (response.data == null || response.data == "")
				{
					$scope.searchErrorMessage = "No Records found";
					$scope.editOwnerSearchResult.data = [];
				} 
				else
				{
					$scope.searchErrorMessage = "";
					$scope.editOwnerSearchResult.data = response.data;
				}
	
			});
	
			$timeout(function () {
				$scope.searchErrorMessage = "";
			}, 4000);
		}
	
	
	$scope.editOwnerSearchResult = {

			enableGridMenus: false,
			enableSorting: false,
			enableFiltering: false,
			enableCellEdit: false,
			enableColumnMenus: false,
			enableHorizontalScrollbar:1,
			enableVerticalScrollbar: 1,
			paginationPageSizes: [5, 10, 20, 30],
			paginationPageSize: 10,
			useExternalPagination: true,

			columnDefs: [
				{
					name: 'applicationId',
					displayName: 'Post Id',
					width:'15%'
				},
				{
					name: 'senderName',
					displayName: "Sender",
					width: '15%'
				},
				{
					name: 'dateReceived',
					displayName: 'Date Received',
					width: '10%'
				},
				{
					name: 'subject',
					displayName: 'Subject',
					width: '15%'
				},
				{
					name: 'priority',
					displayName: 'Priority',
					width: '10%'
						
				},
				{
					name: 'ownerName',
					displayName: 'Owner',
					width: '10%'
				},
				{
					name: 'updatedOwner',
					displayName: 'Update Owner',
					cellTemplate: 'partials/departmentEmployeesDropDown.html',
					width: '15%'
				},
				{
					name:'assign',
					displayName:'Assign',
					width:'10%',
					cellTemplate: '<button type="button" class="btn btn-success assignOwnerButton" ng-click="grid.appScope.assignOwner(row)">Assign</button>'
				},
				{
					name: 'dateAssigned',
					displayName: 'Date Assigned',
					width: '15%'
				},
				{
					name: 'status',
					displayName: 'Status',
					width: '15%'
				},
				{
					name: 'eta',
					displayName: 'ETA',
					width: '15%'
				},
				{
					name: 'documentType',
					displayName: 'Document Type',
					width: '15%'
				},
				{
					name: 'documentRemarks',
					displayName: 'Document Remarks',
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

	
	
	
	/** This method is invoked when the application in ON HOLD State is updated to some other state**/
	$scope.assignOwner=function(row)
	{
		
		if(row.entity.updatedOwner===undefined||row.entity.updatedOwner===null)
		{
			$scope.editOwnerSuccessMessage="";
			$scope.editOwnerErrorMessage="Please select the owner to be assigned";
			
		}
		else
			{
		
			var fd = new FormData();
			fd.append("applicationId",row.entity.applicationId);
			fd.append("senderName",row.entity.senderName);
			fd.append("subject",row.entity.subject);
			fd.append("priority",row.entity.priority);
			fd.append("updatedOwner",row.entity.updatedOwner);
			fd.append("eta",row.entity.eta);
			fd.append("documentRemarks",row.entity.documentRemarks);
			fd.append("documentPath",row.entity.documentPath);
			fd.append("updatedStatus",row.entity.status);
			fd.append("documentType",row.entity.documentType);
			
			var url = "/editApplicationOwnerDH";
			
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
								$scope.editOwnerErrorMessage="";
								$scope.editOwnerSuccessMessage="Application owner successfully updated";
								$scope.editOwnerSearchResult.data=[];
							}
						else
							{
								$scope.editOwnerSuccessMessage ="";
								$scope.editOwnerErrorMessage = "Application Owner could not be updated";

							}
				});
			 
				
			
			}
		$timeout(function () {
			$scope.editOwnerSuccessMessage = "";
			$scope.editOwnerErrorMessage = "";
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

	
	
	$scope.editETA={};
	$scope.searchEditEta = function()
	{
		var searchUrl = "/showEditApplicationDetailsDH?applicationId="+$scope.editETA.editEtaApplicationId;
		
			$http.get(searchUrl)
			.then(function (response) {
				$scope.editETA.editEtaApplicationId = "";
				
				if (response.data == null || response.data == "")
				{
					$scope.editEtaSearchErrorMessage = "No Records found";
					$scope.editEtaSearchResult.data = [];
				} 
				else
				{
					$scope.editEtaSearchErrorMessage = "";
					$scope.editEtaSearchResult.data = response.data;
				}
	
			});
	
			$timeout(function () {
				$scope.editEtaSearchErrorMessage = "";
			}, 4000);
		}

	
	$scope.editEtaSearchResult = {

			enableGridMenus: false,
			enableSorting: false,
			enableFiltering: false,
			enableCellEdit: false,
			enableColumnMenus: false,
			enableHorizontalScrollbar:1,
			enableVerticalScrollbar: 1,
			paginationPageSizes: [5, 10, 20, 30],
			paginationPageSize: 10,
			useExternalPagination: true,

			columnDefs: [
				{
					name: 'applicationId',
					displayName: 'Post Id',
					width:'15%'
				},
				{
					name: 'senderName',
					displayName: "Sender",
					width: '15%'
				},
				{
					name: 'dateReceived',
					displayName: 'Date Received',
					width: '10%'
				},
				{
					name: 'subject',
					displayName: 'Subject',
					width: '15%'
				},
				{
					name: 'priority',
					displayName: 'Priority',
					width: '10%'
						
				},
				{
					name: 'ownerName',
					displayName: 'Owner',
					width: '10%'
				},
				
				{
					name: 'dateAssigned',
					displayName: 'Date Assigned',
					width: '15%'
				},
				{
					name: 'status',
					displayName: 'Status',
					width: '15%'
				},
				{
					name: 'eta',
					displayName: 'ETA',
					width: '15%'
				},
				{
					name: 'updatedEta',
					displayName: 'New ETA',
					cellTemplate: 'partials/datePickerNewApplication.html',
					width:'15%'
				},
				{
					name:'update',
					displayName:'Update ETA',
					width:'10%',
					cellTemplate: '<button type="button" class="btn btn-success assignEtaButton" ng-click="grid.appScope.updateEta(row)">Update</button>'
				},
				{
					name: 'documentType',
					displayName: 'Document Type',
					width: '15%'
				},
				{
					name: 'documentRemarks',
					displayName: 'Document Remarks',
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

	
	
	$scope.updateEta = function(row)
	{
		if(row.entity.updatedEta===undefined||row.entity.updatedEta===null)
			{
				$scope.editEtaSuccessMessage="";
				$scope.editEtaErrorMessage="Please select the new ETA to be updated";

			}
		
		else
			{
				
			var fd = new FormData();
			fd.append("applicationId",row.entity.applicationId);
			fd.append("senderName",row.entity.senderName);
			fd.append("subject",row.entity.subject);
			fd.append("priority",row.entity.priority);
			fd.append("updatedOwner",row.entity.ownerName);
			fd.append("eta",row.entity.updatedEta);
			fd.append("documentRemarks",row.entity.documentRemarks);
			fd.append("documentPath",row.entity.documentPath);
			fd.append("updatedStatus",row.entity.status);
			fd.append("documentType",row.entity.documentType);
			
			var url = "/editApplicationEtaDH";
			
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
								$scope.editEtaErrorMessage="";
								$scope.editEtaSuccessMessage="Application ETA successfully updated";
								$scope.editEtaSearchResult.data=[];
							}
						else
							{
								$scope.editEtaSuccessMessage ="";
								$scope.editEtaErrorMessage = "Application ETA could not be updated";

							}
				});
			 
				
			
			}
		$timeout(function () {
			$scope.editEtaSuccessMessage = "";
			$scope.editEtaErrorMessage = "";
		}, 4000);
		
			
			
			}
	
	
	/** This method updates the details of the post that are entered by the DH in the edit section **/
	$scope.updatePostDetailsDH = function()
	{
		
		if ($scope.postDetails.additionalComments === undefined || $scope.postDetails.additionalComments === null)
		{
			$scope.postDetails.additionalComments = "";
		}
		
		if($scope.postDetails.referenceNumber === undefined || $scope.postDetails.referenceNumber === null)
		{
			$scope.postDetails.referenceNumber = "";
		}		
		
		else
			{
			var applicationId = $scope.postDetails.applicationId;
			var senderName = $scope.postDetails.senderName;
			var pointOfContact = $scope.postDetails.pointOfContact;
			var contactNumber = $scope.postDetails.contactNumber;
			var dateReceived = $scope.postDetails.dateReceived;
			var priority = $scope.postDetails.priority;
			var subject = $scope.postDetails.subject;
			var documentType = $scope.postDetails.documentType;
			var referenceNumber = $scope.postDetails.referenceNumber;
			var additionalComments = $scope.postDetails.additionalComments;
			var file = $scope.postDetails.file;
			
			var updatePost = updatePostService.updatePost(applicationId,senderName,pointOfContact,contactNumber,dateReceived,priority,subject,documentType,referenceNumber,additionalComments,file);

			}
				
		
	}
	
	
/** This method gets the existing document related details which the DEO can edit  **/
	
	$scope.searchPostDetailsUsingApplicationId = function()
	{
		
		if($scope.searchParams.editApplicationId==undefined || $scope.searchParams.editApplicationId==null||$scope.searchParams.editApplicationId=="")
			{
				$scope.getDetailsErrorMessage = "Cannot retreive post details, Please contact your administrator";
			}
		$timeout(function () {
			$scope.getDetailsErrorMessage = "";
		}, 6000)
		
		var getPostDetailUrl = '/getApplicationDetailsById?applicationId='+$scope.searchParams.editApplicationId;
		
		
		$http.get(getPostDetailUrl)
		.then(function(response){ 
			
			$scope.postDetails = response;
			if(response==null||response==undefined||response=="")
				{
					$scope.getDetailsErrorMessage = "Cannot retreive post details, Please contact your administrator";
				}
			
			else
				{
				
					$scope.getDetailsErrorMessage = "";
					if(response.data[0].senderContact == 0 || response.data[0].senderContact == "")
						{
							$scope.postDetails.contactNumber = "";
						}
					
					else
						{
						    $scope.postDetails.applicationId = response.data[0].applicationId;
							$scope.postDetails.senderName = response.data[0].senderName;
							$scope.postDetails.pointOfContact = response.data[0].senderPointOfContact;
							$scope.postDetails.contactNumber = response.data[0].senderContact;
							$scope.postDetails.dateReceived = response.data[0].dateReceived;
							$scope.postDetails.priority = response.data[0].priority;
							$scope.postDetails.referenceNumber = response.data[0].referenceNumber;
							$scope.postDetails.subject = response.data[0].subject;
							$scope.postDetails.documentType = response.data[0].documentType;
							$scope.postDetails.additionalComments = response.data[0].additionalComment;
							
						}
				}
		});	
		
		$timeout(function () {
			$scope.getDetailsErrorMessage = "";
		}, 6000)
		
		
	}
	
	
	

	/** This method updates the details of the post that are entered by the DEO in the edit section **/
	$scope.updatePostDetails = function()
	{
		
		if ($scope.postDetails.additionalComments === undefined || $scope.postDetails.additionalComments === null)
		{
			$scope.postDetails.additionalComments = "";
		}
		
		if($scope.postDetails.referenceNumber === undefined || $scope.postDetails.referenceNumber === null)
		{
			$scope.postDetails.referenceNumber = "";
		}		
		
		else
			{
			var applicationId = $scope.postDetails.applicationId;
			var senderName = $scope.postDetails.senderName;
			var pointOfContact = $scope.postDetails.pointOfContact;
			var contactNumber = $scope.postDetails.contactNumber;
			var dateReceived = $scope.postDetails.dateReceived;
			var priority = $scope.postDetails.priority;
			var subject = $scope.postDetails.subject;
			var documentType = $scope.postDetails.documentType;
			var referenceNumber = $scope.postDetails.referenceNumber;
			var additionalComments = $scope.postDetails.additionalComments;
			var file = $scope.postDetails.file;
			
			var updatePost = updatePostService.updatePost(applicationId,senderName,pointOfContact,contactNumber,dateReceived,priority,subject,documentType,referenceNumber,additionalComments,file);

			}
				
		
	}
	
	
	
});