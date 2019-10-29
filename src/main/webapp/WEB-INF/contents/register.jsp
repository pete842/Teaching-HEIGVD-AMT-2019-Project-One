<div class="container">
    <div class="col-md-4 ml-auto mr-auto">
        <div class="card card-login card-plain">
            <form class="form" method="POST" action="${pageContext.request.contextPath}/registration" id="registerForm">
                <div class="card-header text-center">
                    <div class="logo-container">
                        <img src="./assets/img/now-logo.png" alt="">
                    </div>
                </div>
                <div class="card-body">
                    <div class="input-group no-border input-lg">
                        <div class="input-group-prepend">
                    <span class="input-group-text">
                      <i class="now-ui-icons users_circle-08"></i>
                    </span>
                        </div>
                        <input type="text" class="form-control" placeholder="User Name..." value="${username}" name="username">
                    </div>
                    <div class="input-group no-border input-lg">
                        <div class="input-group-prepend">
                    <span class="input-group-text">
                      <i class="now-ui-icons objects_key-25"></i>
                    </span>
                        </div>
                        <input type="password" placeholder="Password..." class="form-control" value="${password}" name="password"/>
                    </div>
                    <div class="input-group no-border input-lg">
                        <div class="input-group-prepend">
                    <span class="input-group-text">
                      <i class="now-ui-icons objects_key-25"></i>
                    </span>
                        </div>
                        <input type="password" placeholder="Confirm Password..." class="form-control" value="${password_confirm}" name="password_confirm"/>
                    </div>
                    <div class="input-group no-border input-lg">
                        <div class="input-group-prepend">
                    <span class="input-group-text">
                    </span>
                        </div>
                        <input type="text" class="form-control" placeholder="First Name..." value="${firstname}" name="firstname">
                    </div>
                    <div class="input-group no-border input-lg">
                        <div class="input-group-prepend">
                    <span class="input-group-text">
                    </span>
                        </div>
                        <input type="text" class="form-control" placeholder="Last Name..." value="${lastname}" name="lastname">
                    </div>
                    <div class="input-group no-border input-lg">
                        <div class="input-group-prepend">
                    <span class="input-group-text">
                      <i class="now-ui-icons ui-1_email-85"></i>
                    </span>
                        </div>
                        <input type="text" class="form-control" placeholder="Email Address..." value="${email}" name="email">
                    </div>
                </div>
                <div class="card-footer text-center">
                    <button type="submit" class="btn btn-primary btn-round btn-lg btn-block">Create Account</button>
            </form>
        </div>
    </div>
</div>
</div>