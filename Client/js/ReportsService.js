$(document).ready(function () {
    for(var i = 2018; i<2050; i++) {
        $('#exampleFormControlSelect1').append("<option>" + i + "</option>");
    }
    document.getElementById('add-start-date').valueAsDate = new Date("2018-01-01");
    document.getElementById('add-end-date').valueAsDate = new Date();
});

function pie() {
    var start = $('#add-start-date').val();
    var end = $('#add-end-date').val();
    google.charts.load('current', {
        'packages': ['corechart', 'line']
    });
    google.charts.setOnLoadCallback(drawChart);

    var colsArray = [];
    var rowsArray = [];
    var bucketSort = [];
    var totalOrders = 0;
    var totalPrice = 0;
    function drawChart() {
        colsArray.push({
            id: "",
            label: 'Topping',
            pattern: "",
            type: 'string'
        });
        colsArray.push({
            id: "",
            label: 'Slices',
            pattern: "",
            type: 'number'
        });

        try {
            var xhr = new XMLHttpRequest();
            xhr.open("GET", "http://localhost:8080/appBackend/OrdersController?shopName=" + sessionStorage.getItem('storename')+"&startDate="+start+"&endDate="+end, true);

            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    var obj = JSON.parse(xhr.responseText);
                    totalOrders = obj.length;

                    for (var i = 0; i < obj.length; i++) {
                        var items = obj[i].items.split(",");
                        for (var key in items) {
                            if (!isNaN(parseInt(items[key], 10))) {
                                bucketSort[parseInt(items[key], 10)] = 0;
                            }
                        }
                    }

                    for (var i = 0; i < obj.length; i++) {
                        var items = obj[i].items.split(",");
                        for (var key in items) {
                            if (!isNaN(parseInt(items[key], 10))) {
                                bucketSort[parseInt(items[key], 10)]++;
                            }
                        }
                    }
                    getNames();
                } else if (xhr.status === 0) {
                    alert('Server is offline!');
                }
            }
            xhr.send();
        } catch (exception) {
            alert("Request failed");
        }
    }

    function getNames() {
        var str = "";
        for (var i = 0; i < bucketSort.length; i++) {
            if (bucketSort[i] !== undefined) {
                str += i + ",";
            }
        }

        try {
            var xhr = new XMLHttpRequest();
            xhr.open("GET", "http://localhost:8080/Server/ItemsController?dbName=" + sessionStorage.getItem('storename') + "&filter=true&items=" + str, true);

            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    var obj = JSON.parse(xhr.responseText);
                    for (var i = 0; i < obj.length; i++) {
                        for (var j = 0; j < bucketSort.length; j++) {
                            if (bucketSort[j] !== undefined) {
                                if (j == obj[i].id)
                                    rowsArray.push({
                                        c: [{
                                            v: "Title : " + obj[i].title+" Profit : " + obj[i].price*bucketSort[j],
                                            f: null
                                        }, {
                                            v: bucketSort[j],
                                            f: null
                                        }]
                                    });
                            }
                        }
                        totalPrice += parseInt(obj[i].price, 10);
                    }
                    var data = new google.visualization.DataTable({
                        cols: colsArray,
                        rows: rowsArray
                    });
                    var chart = new google.visualization.PieChart(document.getElementById('pie_div'));
                    chart.draw(data, {
                        title: 'Showing data for all orders : \nTotal Orders : ' + totalOrders + '\nTotal profit : ' + totalPrice,
                        width: '100%',
                        height: 500
                    });
                } else if (xhr.status === 0) {
                    alert('Server is offline!');
                }
            }
            xhr.send();
        } catch (exception) {
            alert("Request failed");
        }
    }
}

function line() {
    google.charts.load('current', {
        packages: ['corechart']
    });
    google.charts.setOnLoadCallback(drawBasic);

    function drawBasic() {
        
        var year = $('#exampleFormControlSelect1').val();
        var colsArray = [];
        var rowsArray = [];

        var months = ["Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sep", "Oct", "Nov", "Dec"];
        var bucketSort = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];

        colsArray.push({
            id: "",
            label: 'X',
            pattern: "",
            type: 'number'
        });
        colsArray.push({
            id: "",
            label: 'Orders',
            pattern: "",
            type: 'number'
        });

        function monthToIndex(str) {
            for (var i = 0; i < 12; i++) {
                if (str === months[i]) return i;
            }
        }
        try {
            var xhr = new XMLHttpRequest();
            xhr.open("GET", "http://localhost:8080/appBackend/OrdersController?shopName=" + sessionStorage.getItem('storename')+"&startDate="+year, true);

            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    var obj = JSON.parse(xhr.responseText);
                    for (var i = 0; i < obj.length; i++) {
                        var month = obj[i].date.split(' ');
                        console.log(monthToIndex(month[0]));
                        bucketSort[monthToIndex(month[0])]++;
                        
                    }
                    console.log(bucketSort);
                    for (var i = 0; i < bucketSort.length; i++) {
                        rowsArray.push({
                            c: [{
                                v: i,
                                f: months[i]
                            }, {
                                v: bucketSort[i],
                                f: null
                            }]
                        });
                    }

                }
                render();
            }
            xhr.send();
        } catch (exception) {
            alert("Request failed");
        }

        function render() {
            var options = {
                curveType: 'function',
                hAxis: {
                    title: 'Months',
                    gridlines: {
                        color: '#f3f3f3',
                        count: 4
                    },
                    ticks: [{
                            v: 0,
                            f: 'Jan'
                        }, {
                            v: 1,
                            f: 'Feb'
                        }, {
                            v: 2,
                            f: 'Mar'
                        }, {
                            v: 3,
                            f: 'Apr'
                        },
                        {
                            v: 4,
                            f: 'May'
                        }, {
                            v: 5,
                            f: 'Jun'
                        }, {
                            v: 6,
                            f: 'Jul'
                        }, {
                            v: 7,
                            f: 'Aug'
                        },
                        {
                            v: 8,
                            f: 'Sep'
                        }, {
                            v: 9,
                            f: 'Oct'
                        }, {
                            v: 10,
                            f: 'Nov'
                        }, {
                            v: 11,
                            f: 'Dec'
                        }
                    ],
                },
                vAxis: {
                    title: 'Orders',
                },
                width: '100%',
                height: 500,
            };
            var data = new google.visualization.DataTable({
                cols: colsArray,
                rows: rowsArray
            });
            var chart = new google.visualization.AreaChart(document.getElementById('line_div'));
            chart.draw(data, options);
        }
    }
}