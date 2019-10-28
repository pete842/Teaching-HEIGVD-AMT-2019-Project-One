<div class="container">
    <div class="col-md-4 ml-auto mr-auto">
        <div class="card card-login card-plain">
            <form class="form" method="POST" action="${pageContext.request.contextPath}/login">
                <div class="card-header text-center">
                    <div class="logo-container">
                        <img src="${pageContext.request.contextPath}/assets/img/now-logo.png" alt="">
                    </div>
                </div>
                <div class="card-body">
                    <div class="input-group no-border input-lg">
                        <div class="input-group-prepend">
                    <span class="input-group-text">
                      <i class="now-ui-icons users_circle-08"></i>
                    </span>
                        </div>
                        <input type="text" class="form-control" placeholder="User Name..." name="username"/>
                    </div>
                    <div class="input-group no-border input-lg">
                        <div class="input-group-prepend">
                    <span class="input-group-text">
                      <i class="now-ui-icons objects_key-25"></i>
                    </span>
                        </div>
                        <input type="password" placeholder="Password..." class="form-control"
                               name="password"/>
                    </div>
                </div>
                <div class="card-footer text-center">
                <button type="submit" class="btn btn-primary btn-round btn-lg btn-block">Get Started
                </button>
                <div class="pull-left">
                    <h6>
                        <a href="${pageContext.request.contextPath}/registration" class="link">Create Account</a>
                    </h6>
                </div>
            </form>
        </div>
    </div>
</div>