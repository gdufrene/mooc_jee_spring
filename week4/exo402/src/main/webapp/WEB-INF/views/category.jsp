<%@include file="_header.jsp" %>
<%-- import required classes --%>

<ul class="articles">
<%-- Iterate through articles ... --%>
<li>
	<a href="#">
		<span class="price">
			<%-- show price as X,XX --%> &euro;</span>
		<%-- 
			show product image, you can use 'https://static1.chronodrive.com'
			as base URL and img path to complete the image URL 
		--%>
		<img src="IMG.gif"/><br/>
		<%-- show product name --%> <br/>
	</a>
</li>

</ul>
<%@include file="_footer.jsp" %>