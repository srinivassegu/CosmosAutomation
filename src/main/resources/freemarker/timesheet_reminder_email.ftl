<html>
	<head>
	        <title>Timesheet Reminder</title>
	        <style type="text/css">
	            *{margin:0; padding:0;}
	            body{font:12px/16px Arial, Helvetica, sans-serif; padding:10px;}
	            .table{width:100%; border:#ccc solid 1px; margin-bottom:30px;}
	            .table td{padding:10px; border:#ccc solid 1px; border-right:none; border-bottom:none; font-size:12px;}
	            h3{display:block; text-align:left; padding:10px; font-weight:normal; font-size:18px;
	            background:#09F; color:#fff;}
	            .table tr.main_heading td{background:#999; padding:0;}
	            .table tr.main_heading td h3{background:inherit;}
	            .table tr.heading td{border:#ccc solid 1px; border-right:none;}
	            .table tr.heading td h3{background:#fff; color:#444; font-size:13px;font-weight:bold; padding:0;}
	        </style>
	    </head>
	
	
	<body id="body">
				<p>Hi ${consultantFirstName},</p>
				<p>&nbsp;</p>
				<p style="padding-left: 30px;">Please submite timesheets for the week period from ${startDate} to ${endDate} here,&nbsp;${submissionLink}.</p>
				<p style="padding-left: 30px;">&nbsp;</p>
				<p><em>Thanks and Regards,</em></p>
				<p><em>${employeeName}</em></p>
				<p><em>${employeeDepartment}</em></p>
				<p><em>Tel: ${mobileContact}</em></p>
				<p><em>Fax: ${faxContact}</em></p>
				<p><em>Office Address${organizationAddress}</em></p>
	</body>
</html>