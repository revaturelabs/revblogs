app.controller("BlogIndexController", ["$scope", "$http", function($scope, $http) {
	// Domain DTO mockup
	$scope.posts = {				/* 10 posts per page is default; can be changed by using ?perPage=25 */
		page: 1,					/* current page */
		totalPages: 7,				/* total number of pages */
		totalPosts: 65,				/* total number of posts */
		prev: null,					/* link to previous set of posts */
		next: "/api/posts?page=2",	/* link to next set of posts */
		posts: [					/* array of post objects */
					{
/* post id */			id: 1,
/* post title */		title: "The Joy of Coding",
/* post subtitle */		subtitle: "40 ways to enhance your productivity whilst coding",
/* post content, plain 
   text (use JS to 
   clamp length) */		content: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
/* post tag array */	tags: ["Java", ".NET", "General Programming", "Productivity"],
/* post author
   object */			author: {
   							id: 1,
							name: "Trey McDeane",
/* link to user posts */	link: "/author/1/posts"
						},
/* post date as ms */	postDate: 1472588905064
					},
					{
						id: 2,
						title: "Blah Man Blah",
						subtitle: "This is a thing",
						content: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
						tags: ["Java", ".NET", "General Programming", "Productivity"],
						author: {
   							id: 1,
							name: "Trey McDeane",
							link: "/author/1/posts"
						},
						postDate: 1472588905064
					},
			]
		}
}]);