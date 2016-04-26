var app = angular.module("uwreg", ['ngRoute']);

app.config(function($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: '_home.html'
        })
        .when('/register', {
            templateUrl: '_register.html',
            controller: 'registrationCtrl'
        });
});


var flattener = function(courses) {
    console.log("Flattening:");
    console.log(courses);
    var flatten = [];
    for (i in courses) {
        flatten.push ({ type: 'course', row: courses[i] });
        for (j in courses[i].notes) {
            flatten.push ({ type: 'note', row: { note: courses[i].notes[j] } });
        }
    }
    console.log("Returning:");
    console.log(flatten);
    return flatten;
};


app.controller("uwregCtrl", function($scope, $http) {
    $scope.registered_for = [];
    $scope.courses_to_add = [];
    $scope.student = {};
    
    $scope.loadRegistration = function() {
        $http.get("/courses_registered")
            .success(function(response) {
                $scope.registered_for = flattener(response);
            });
    };
    
    $scope.loadStudent = function() {
        $http.get("/username")
            .success(function(response) {
                $scope.student = response;
            });
    };
    
    $scope.$on('update', function(event, args) {
    	$scope.loadRegistration ();
    });
    
    $scope.loadRegistration ();
    $scope.loadStudent ();
});

app.controller("registrationCtrl", function($scope, $http, $location) {
	$scope.registered_for = [];
    $scope.courses_to_add = [];
    $scope.unflattened_courses_to_add = [];
    $scope.student = {};
    $scope.search_results = [];
    $scope.unflattened_search_results = [];
    $scope.status = "";
    $scope.search_crn = "";
    $scope.search_dept = "";
    $scope.search_cnumber = "";
    $scope.search_title = "";
    $scope.search_options = [{ value: "none",     label: "Choose..."},
                             { value: "crn",  	  label: "CRN"},
                             { value: "cnumber",  label: "Dept and Number"},
                             { value: "title",    label: "Title"},
                             ];
    $scope.search_by = $scope.search_options[0];
    $scope.error_msg = "";
    
    $scope.loadRegistration = function() {
        $http.get("/courses_registered")
            .success(function(response) {
            	for (var i in response) {
            		response[i]._keep = true;
            	} 
                $scope.registered_for = flattener(response);
            });
    };
    
    $scope.loadStudent = function() {
        $http.get("/username")
            .success(function(response) {
                $scope.student = response;
            });
    };
    
    $scope.addCrn = function (crn) {
    	for (var i in $scope.unflattened_search_results) {
    		if ($scope.unflattened_search_results[i].crn == crn) {
    			$scope.unflattened_courses_to_add.push($scope.unflattened_search_results[i]);
    			$scope.unflattened_search_results.splice(i, i+1);
    			$scope.search_results = flattener($scope.unflattened_search_results);
    			$scope.courses_to_add = flattener($scope.unflattened_courses_to_add);
    			break;
    		}
    	}
    }
    
    $scope.unadd = function (crn) {
    	for (var i in $scope.unflattened_courses_to_add) {
    		if ($scope.unflattened_courses_to_add[i].crn == crn) {
    			$scope.unflattened_search_results.push($scope.unflattened_courses_to_add[i]);
    			$scope.unflattened_courses_to_add.splice(i, i+1);
    			$scope.search_results = flattener($scope.unflattened_search_results);
    			$scope.courses_to_add = flattener($scope.unflattened_courses_to_add);
    			break;
    		}
    	}
    }
    
    $scope.updateRegistration = function () {
    	var add  = [];
    	var drop = [];
    	
    	$scope.error_msg = "";
    	
    	for (var i in $scope.unflattened_courses_to_add) {
    		add.push($scope.unflattened_courses_to_add[i].crn); 
    	}
    	
    	for (var i in $scope.registered_for) {
    		if ($scope.registered_for[i].type == 'course' &&
    				!$scope.registered_for[i].row._keep) {
    			drop.push ($scope.registered_for[i].row.crn);
    		}
    	}
    	
    	var param_add  = add.join();
    	var param_drop = drop.join();
    	
        $http.get("/update_registration?add="+param_add+"&drop="+param_drop)
	        .success(function(response) {
	        	if (response.status == "success") {
	        		$scope.$emit('update', []);
	        		$location.path("/");
	        	}
	        	else {
	        		$scope.error_msg = response.error_msg;
	        	}
	            
	        });
	}
    
    searchResponse = function(response) {
    	$scope.unflattened_search_results = response;
        $scope.search_results = flattener(response);
        $scope.status = "Search complete: " + response.length;
        if (response.length == 1) {
            $scope.status += " course found";
        }
        else {
            $scope.status += " courses found";                	
        }
    }
    
    $scope.searchByCrn = function() {
        $scope.search_results = [];
        $scope.status = "Searching...";
        $http.get("/search/crn?crn="+$scope.search_crn)
            .success(searchResponse);
    };
    $scope.searchByCnumber = function() {
        $scope.search_results = [];
        $scope.status = "Searching...";
        $http.get("/search/cnumber?dept="+$scope.search_dept+"&cnumber="+$scope.search_cnumber)
        	.success(searchResponse);
    };
    $scope.searchByTitle = function() {
        $scope.search_results = [];
        $scope.status = "Searching...";
        $http.get("/search/title?title="+encodeURIComponent($scope.search_title))
        	.success(searchResponse);
    };

    
    $scope.loadRegistration ();
    $scope.loadStudent ();
});


