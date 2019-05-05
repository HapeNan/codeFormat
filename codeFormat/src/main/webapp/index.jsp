<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<!--[if lt IE 7 ]><html class="ie ie6" lang="en"> <![endif]-->
<!--[if IE 7 ]><html class="ie ie7" lang="en"> <![endif]-->
<!--[if IE 8 ]><html class="ie ie8" lang="en"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--><html lang="en"> <!--<![endif]--><head>

    <!-- Le Basic Page Needs
    ================================================== -->
    <meta charset="utf-8">
    <title>代码格式工厂</title>
    <meta name="description" content="Stuff made by Jamie">
    <meta name="author" content="Jamie Beach">

    <!-- Le Mobile Specific Metas
    ================================================== -->
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <!-- Le CSS
    ================================================== -->
	<link rel="stylesheet" href="css/bootstrap.min.css">
	<link rel="stylesheet" href="css/starter.css">
	<link rel="stylesheet" href="css/style2.css">
	<link rel="stylesheet" href="css/portfolio.css">

	<!--[if lt IE 9]>
		<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->
    
	<!-- Javascript Pre-loads
	================================================== -->
    <script src="js/modernizr.custom.js"></script>

	<!-- Le Fonts
	================================================== -->
<!-- <link href='http://fonts.googleapis.com/css?family=Lato:100,300,400,700' rel='stylesheet' type='text/css'> -->

	<!-- Le Favicons
	================================================== -->	
	<link rel="shortcut icon" href="images/favicon.ico">
	<link rel="apple-touch-icon" href="images/apple-touch-icon.png">
	<link rel="apple-touch-icon" sizes="72x72" href="images/apple-touch-icon-72x72.png">
	<link rel="apple-touch-icon" sizes="114x114" href="images/apple-touch-icon-114x114.png">

</head>
<body id="top">

<!-- Header -->
<header class="subheader">
    <div class="container">
        <div class="row">
            <div class="span12 align-left" style="position:relative">
		<div style="position:absolute;margin-top:15px;z-index:2"> Code Formatter</div>
            </div>
        </div>
    </div>
</header>


<!-- Le Navigation -->
<nav class="animated fadeIn" data-spy="affix" data-offset-top="400">
    <div class="container">
        <div class="row">
            <ul id="top-menu" style="float:left;">
              <li class="span1 active"><span class="hidden">Top</span></a></li>
              <li class="span2"><a href="/codeFormat/index">刷新页面</a></li>
            </ul>
	</div>
    </div>
</nav>


<div class="clearfix"> </div>
</div></div>



<!-- About Me -->
<section id="subsection">
	<div class="container">
		<div class="row">
			<div class="span12" style="margin-top:20px;">
				<h1>代码格式工厂</h1>
			</div>
		</div>
		<div class="row">
			<div class="span1"></div>
			<div class="span10 align-left" style="margin-bottom:25px; margin-left:200px;">
				<p>请在下列框中输入待格式化的代码.</p>
				<p>选择语言类型，勾选功能框后，后单击按钮,将弹出一个带有语法高亮代码的新窗口.</p>
			    <p>可以将格式化后的代码复制并粘贴到文档中.</p>
			</div>
			</div class="span1"></div>
		</div>
		<div class="row">
			<div style="width:100%; text-align:center">

        <div>
		<form action="formatCode" target="codepopup" method="post">
		<center>
			<table border="10" style="width:500px">
				<tr>
					<td colspan="2">
						<p align="left">代码:</p>
						<textarea name="code" style="width:800px" id="code" cols="110" rows="20"></textarea>
					</td>
				</tr>
				<tr>
					<td style="width:300px">
						选择语言: 
						<select name="class" id="class">
							<option value="c++">C, C++</option>							
							<option value="c#">C#</option>							
							<option value="css">CSS</option>							
							<option value="delphi">Delphi, Pascal</option>							
							<option value="xml">XML</option>														
							<option value="java">Java</option>																					
							<option value="javascript">Javascript</option>																												
							<option value="php">PHP</option>							
							<option value="python">Python</option>							
							<option value="ruby">Ruby</option>							
							<option value="sql">SQL</option>							
							<option value="vb">VB</option>							
							<option value="xml">HTML</option>														
						</select>
					</td>
					<td>
					 <input type="checkbox" name="checks" value="switch"> switch/if替换&nbsp; 
                     <input type="checkbox" name="checks" value="if"> 简单条件替换 &nbsp;
                 
					 <input type="submit" value="显示代码"/>
					</td>
				</tr>
				</table>
				</center>
		</form>
		</div>
		</div>
			</div>
		</div>
</section>



<!-- Le Javascript -->
<!--  <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>-->
<script src="js/jquery.easing.1.3.js"></script>
<!-- <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?sensor=false"></script>-->
<script src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/jquery.flexslider.js"></script>
<script src="js/grid.js"></script>
<script src="js/scripts.js"></script>
<script>
    $(function() {
        Grid.init();
    });
</script>


<!-- Start of StatCounter Code for Default Guide -->
<script type="text/javascript">
var sc_project=5681546; 
var sc_invisible=1; 
var sc_security="58989b7a"; 
</script>
<!--<script type="text/javascript" src="http://www.statcounter.com/counter/counter.js"></script>
<noscript><div class="statcounter"><a title="web analytics"
href="http://statcounter.com/" target="_blank"><img
class="statcounter"
src="http://c.statcounter.com/5681546/0/58989b7a/1/"
alt="web analytics"></a></div></noscript>-->
<!-- End of StatCounter Code for Default Guide -->
</body>
</html>
