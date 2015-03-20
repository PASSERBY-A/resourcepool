$(document).ready(function() {
			function abc(w)
			{
				alert(w);
			}

			var name = $( "#name" ),
				//id = $( "#id" ),
				//allFields = $( [] ).add( name ).add( id ),
				tips = $( ".validateTips" );

			function updateTips( t ) {
				tips
					.text( t )
					.addClass( "ui-state-highlight" );
				setTimeout(function() {
					tips.removeClass( "ui-state-highlight", 1500 );
				}, 500 );
			}

			function checkLength( o, n, min, max ) {
				if ( o.val().length > max || o.val().length < min ) {
					o.addClass( "ui-state-error" );
					updateTips( "Length of " + n + " must be between " +
						min + " and " + max + "." );
					return false;
				} else {
					return true;
				}
			}

			function checkRegexp( o, regexp, n ) {
				if ( !( regexp.test( o.val() ) ) ) {
					o.addClass( "ui-state-error" );
					updateTips( n );
					return false;
				} else {
					return true;
				}
			}

			$( "#attrEdit-form" ).dialog({
				autoOpen: false,
				height: 300,
				width: 400,
				modal: true,
				buttons: {
				"选属起始节点": function() {
					var openUrl="../ciMgr/ciSelected.jsp?isSource=true";
					$("#openRoleDiv").dialog();
					//window.open(openUrl, "newwindow");
				},
				"选属目标节点": function() {
					var openUrl="../ciMgr/ciSelected.jsp?isSource=false";
					
					window.open(openUrl, "newwindow");
					},
					"Save Atrribute": function() {
						var bValid = true;
						//allFields.removeClass( "ui-state-error" );

						//bValid = bValid && checkLength( name, "name", 1, 255 );
						//bValid = bValid && checkLength( id, "id", 1, 36 );

						//bValid = bValid && checkRegexp( name, /^[a-z]([0-9a-z_])+$/i, "ClassName may consist of a-z, 0-9, underscores, begin with a letter." );
						// From jquery.validate.js (by joern), contributed by Scott Gonzalez: http://projects.scottsplayground.com/email_address_validation/
						//bValid = bValid && checkRegexp( id, /^[a-z]([0-9a-z_])+$/i, "ClassName may consist of a-z, 0-9, underscores, begin with a letter." );

						if ( bValid ) {
							saveAttr();
							$( this ).dialog( "close" );
						}
					},
					Cancel: function() {
						$( this ).dialog( "close" );
					}
				},
				close: function() {
					//allFields.val( "" ).removeClass( "ui-state-error" );
				}
			});
});
