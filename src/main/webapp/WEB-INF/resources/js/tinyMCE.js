/**
 * 
 */
tinymce.init({ 
	selector:'textarea',
	plugins: ['codesample','emoticons',
	          'print',
	          'textcolor colorpicker textpattern',
	          'insertdatetime nonbreaking save table contextmenu directionality'],
	toolbar: 'insertfile undo redo | styleselect forecolor backcolor | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | codesample | emoticons |  print',
	menubar: ["default","tools"],
	setup: function(editor){
		editor.addMenuItem('s3', {
			 text: 'Upload Picture',
		      context: 'tools',
		      onclick: function(){
		        //to do
		      }
		});
	},
	imagetools_cors_hosts: ['tinymce.com', 'codepen.io'],
	  content_css: [
	    '//fonts.googleapis.com/css?family=Lato:300,300i,400,400i',
	    '//cdnjs.cloudflare.com/ajax/libs/prism/0.0.1/prism.css',
	    '//www.tinymce.com/css/codepen.min.css',
	    '//fonts.googleapis.com/css?family=Source+Sans+Pro'
	  ]
	  
});

