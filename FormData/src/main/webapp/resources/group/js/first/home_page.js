function arrangeHomeWindow(type) {
    
    var bo = $('#main-body');
    
    function getNavTab() {
        var navTab = '<!-- Sidebar and navbar -->' +
                        '<nav class="navbar navbar-inverse">' +
                          '<div class="container-fluid">' +
                            '<div class="navbar-header">' +
                              '<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">' +
                                '<span class="icon-bar"></span>' +
                                '<span class="icon-bar"></span>' +
                                '<span class="icon-bar"></span>              ' +          
                              '</button>' +
                              '<a class="navbar-brand" href="#">Castle Project</a>' +
                            '</div>' +
                            '<div class="collapse navbar-collapse" id="myNavbar">' +
                              '<ul class="nav navbar-nav n-header">' +
                        '<!--        <li><a href="iwelcome2.html">Home</a></li>' +
                                '<li class="active"><a href="#">Rooster</a></li>' +
                                '<li><a href="CalendarDate.html">Due Dates</a></li>-->' +
                              '</ul>' +
                              '<ul class="nav navbar-nav navbar-right">' +
                                 ' <li class="dropdown">' +
                                        '<a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i><span id="user-name"> Instructor </span><b class="caret"></b></a>' +
                                        '<ul class="dropdown-menu">' +
                                            '<li class="divider"></li>' +
                                            '<li><a href="#"><i class="fa fa-fw fa-power-off"></i> Log Out</a></li>' +
                                        '</ul>' +
                                  '</li>' +
                              '</ul>' +
                            '</div>' +
                          '</div>' +
                        '</nav>' ;
        
    return navTab;
    }
    
    function instructorPageWrapper() {
        
        var wrap = '<div id="page-wrapper">' +
                    '<!-- Main Container -->' +
                    '<div class="container-fluid">' +
                        '<div class="row-fluid">' +

                             '<!-- Sidebar -->' +
                         '<div class="col-sm-2">' +
                              '<ul class="nav nav-pills nav-stacked sidenav">' +
                                '<li class="active"><a href="#section1">Experiments</a></li>' +
                                '<li><a href="#section2">Grade Center</a></li>' +
                                 '<li><a href="#section3">User Access</a></li>' +
                             ' </ul><br>' +
                          '</div>' +
                            '<!-- Information Window -->' +

                            '<div id="main-container">' +
                            '</div>' +

                        '</div>' +
                    '</div>' +
                '</div>';
        
        return wrap;
    }
    
    bo.html("");
    bo.append(getNavTab);
    bo.append(instructorPageWrapper);
    
    tabFunctionality();
}
