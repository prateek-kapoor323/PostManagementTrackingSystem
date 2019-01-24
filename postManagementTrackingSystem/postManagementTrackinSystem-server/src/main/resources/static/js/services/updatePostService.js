deoEdit.service('updatePostService', ['$http', function ($http, $timeout) {
	
	this.updatePost = function(applicationId,senderName,pointOfContact,contactNumber,dateReceived,priority,subject,documentType,additionalComments,file)
	{
		var fd = new FormData();
		var file = document.getElementById('file').files[0];
		if(file===undefined)
			{
						fd.append("applicationId",applicationId);
						fd.append("senderName",senderName);
						fd.append("pointOfContact",pointOfContact);
						fd.append("contactNumber",contactNumber);
						fd.append("dateReceived",dateReceived);
						fd.append("priority",priority);
						fd.append("subject",subject);
						fd.append("typeOfDocument",documentType);
						fd.append("additionalComment",additionalComments);
						//fd.append("file",file);
						
						
						$http({
							method: 'POST',
							url: '/editPostDetailsWithoutFile',
							data: fd,
							headers: {
								'Content-Type': undefined
							},
							transformRequest: angular.identity,
							transformResponse: [function (data) {
								thisIsResponse = data;
								return data;

							}]
						}).then(function (response) {
							
							if(response.data>0)
							{
								document.getElementById("updateErrorMessage").innerHTML = "";
								document.getElementById("updateSuccessMessage").innerHTML = "Post details updated successfully";
							}
						else
							{
								document.getElementById("updateSuccessMessage").innerHTML = "";
								document.getElementById("updateErrorMessage").innerHTML = "Post details could not be updated, Please contact your administrator";
							}
						
						setTimeout(function () {
							document.getElementById("updateSuccessMessage").innerHTML = "";
							document.getElementById("updateErrorMessage").innerHTML = "";
						}, 10000);

						document.getElementById('updateForm').reset();
						return response.data;

						});

			}
		
				else
					{
						
						fd.append("applicationId",applicationId);
						fd.append("senderName",senderName);
						fd.append("pointOfContact",pointOfContact);
						fd.append("contactNumber",contactNumber);
						fd.append("dateReceived",dateReceived);
						fd.append("priority",priority);
						fd.append("subject",subject);
						fd.append("typeOfDocument",documentType);
						fd.append("additionalComment",additionalComments);
						fd.append("file",file);
						
						
						$http({
							method: 'POST',
							url: '/editPostDetailsWithFile',
							data: fd,
							headers: {
								'Content-Type': undefined
							},
							transformRequest: angular.identity,
							transformResponse: [function (data) {
								thisIsResponse = data;
								return data;
		
							}]
						}).then(function (response) {
							
							if(response.data>0)
								{
								document.getElementById("updateSuccessMessage").innerHTML = "Post details updated successfully";
								}
							else
								{
									document.getElementById("updateErrorMessage").innerHTML = "Post details could not be updated, Please contact your administrator";
								}
							
							setTimeout(function () {
								document.getElementById("updateSuccessMessage").innerHTML = "";
								document.getElementById("updateErrorMessage").innerHTML = "";
							}, 10000);

							document.getElementById('updateForm').reset();
							return response.data;
		
						});
		
					}
				
			}

}]);