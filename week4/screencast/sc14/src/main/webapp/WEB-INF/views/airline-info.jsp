<%@ include file="layout-header.jsp" %>
<div class="col-md-6 col-md-offset-3">
<h2>
  <span class="glyphicon glyphicon-plane" aria-hidden="true"></span>
  Airline Info
</h2>
<form>

<!--

TODO: check "xxxx" on each form input an assign its value from 
      the model attribute object "airline"

-->

  <div class="form-group">
    <label for="fId">Id</label>

    <input 
	  value="xxxx"
	  type="text" class="form-control" id="fId" placeholder="Id">
  </div>
  <div class="form-group">
    <label for="fName">Name</label>
    <input 
	  value="xxxx"
	  type="text" class="form-control" id="fName" placeholder="Name">
  </div>
  <div class="form-group">
    <label for="fAlias">Alias</label>
    <input 
	  value="xxxx"
	  type="text" class="form-control" id="fAlias" placeholder="Alias">
  </div>
  <div class="form-group">
    <label for="fCountry">Country</label>
    <input 
	  value="xxxx"
	  type="text" class="form-control" id="fCountry" placeholder="Country">
  </div>
  <div class="form-group">
    <label for="fCallsign">Callsign</label>
    <input 
	  value="xxxx"
	  type="text" class="form-control" id="fCallsign" placeholder="Callsign">
  </div>
  <div class="form-group col-md-6">
    <label for="fIata">IATA</label>
    <input 
	  value="xxxx"
	  type="text" class="form-control" id="fIata" placeholder="iata code">
  </div>
  <div class="form-group col-md-6">
    <label for="fIcao">ICAO</label>
    <input 
	  value="xxxx"
	  type="text" class="form-control" id="fIcao" placeholder="icao code">
  </div>
  <div class="checkbox">
    <label>
		<!-- 
		  xxxx
		  TODO: Find how to check the box if airline is active ! 
		-->
      <input id="fActive" type="checkbox" /> Active
    </label>
  </div>
  
  <button type="submit" class="btn btn-primary">
    <span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
    Ok
  </button>
  
</form>
</div>
<%@ include file="layout-footer.jsp" %>