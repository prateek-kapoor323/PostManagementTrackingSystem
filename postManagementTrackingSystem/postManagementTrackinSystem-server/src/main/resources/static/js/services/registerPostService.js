deoHome.service('registerPostService', ['$http', function ($http, $timeout) {

	this.submitPost = function (senderName, pointOfContact, contactNumber, dateReceived, priority, subject, documentType,referenceNumber, ownerName, additionalComments, file, uploadUrl) {
		var fd = new FormData();
		var file = document.getElementById('file').files[0];
		fd.append('senderName', senderName);
		fd.append('pointOfContact', pointOfContact);
		fd.append('contactNumber', contactNumber);
		fd.append('dateReceived', dateReceived);
		fd.append('priority', priority);
		fd.append('subject', subject);
		fd.append('documentType', documentType);
		fd.append('referenceNumber', referenceNumber);
		fd.append('ownerName', ownerName);
		fd.append('additionalComments', additionalComments);
		fd.append('file', file);

		$http({
			method: 'POST',
			url: uploadUrl,
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
			if (response.data.length > 20) 
			{
				
				document.getElementById("Error").innerHTML = "";
				document.getElementById("Error").innerHTML = thisIsResponse;
			}
			else {
				
				document.getElementById("Success").innerHTML = "";
				document.getElementById("Error").innerHTML = "";
				document.getElementById("Success").innerHTML = "Your post has been Registered, Please note the Post Id for your reference: " + thisIsResponse;
			}
			setTimeout(function () {
				document.getElementById("Success").innerHTML = "";
				document.getElementById("Error").innerHTML = "";
			}, 10000);

			document.getElementById('uploadformid').reset();

			return response.data;

		});


	}
}]);