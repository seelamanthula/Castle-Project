var X = XLSX;
var XW = {
	/* worker message */
	msg: 'xlsx',
	/* worker scripts */
	/* noxfer: './lib/dist/xlsxworker.js' */
	 noxfer: './resources/lib/dist/xlsxworker.js'
};
var use_worker = typeof Worker !== 'undefined';
if(!use_worker) {
	document.getElementsByName("useworker")[0].disabled = true;
	document.getElementsByName("useworker")[0].checked = false;
}
    
var wtf_mode = false;
function fixdata(data) {
	var o = "", l = 0, w = 10240;
	for(; l<data.byteLength/w; ++l) o+=String.fromCharCode.apply(null,new Uint8Array(data.slice(l*w,l*w+w)));
	o+=String.fromCharCode.apply(null, new Uint8Array(data.slice(l*w)));
	return o;
}
function xw_noxfer(data, cb) {
	var worker = new Worker(XW.noxfer);
	worker.onmessage = function(e) {
		switch(e.data.t) {
			case 'ready': break;
			case 'e': console.error(e.data.d); break;
			case XW.msg: cb(JSON.parse(e.data.d)); break;
		}
	};
    var arr = btoa(fixdata(data));
    	worker.postMessage({d:arr});
}

function xw(data, cb) {
        xw_noxfer(data, cb);
}
function get_radio_value( radioName ) {
	var radios = document.getElementsByName( radioName );
	for( var i = 0; i < radios.length; i++ ) {
		if( radios[i].checked || radios.length === 1 ) {
			return radios[i].value;
		}
	}
}
function to_json(workbook) {
	var result = {};
	workbook.SheetNames.forEach(function(sheetName) {
		var roa = X.utils.sheet_to_row_object_array(workbook.Sheets[sheetName]);
		if(roa.length > 0){
			result[sheetName] = roa;
		}
	});
	return result;
}

function process_wb(wb) {
	var output = "";
	output = JSON.stringify(to_json(wb), 2, 2);
    
    console.log('In Process');
    
    arrangeJSONClassInformation(to_json(wb));
    
    if(typeof console !== 'undefined') console.log("output", new Date());
}
var drop;
function handleDrop(e) {
	e.stopPropagation();
	e.preventDefault();
	var files = e.dataTransfer.files;
	var f = files[0];
	{
		var reader = new FileReader();
		var name = f.name;
		reader.onload = function(e) {
			if(typeof console !== 'undefined') 
                console.log("onload", new Date(), use_worker);
			var data = e.target.result;
            console.log('use workers');
            xw(data, process_wb);
		};
        reader.readAsArrayBuffer(f);
	}
}
function handleDragover(e) {
	e.stopPropagation();
	e.preventDefault();
	e.dataTransfer.dropEffect = 'copy';
}

var drop;

var xlf;
function handleFile(e) {
    
    var files = e.target.files;
	var f = files[0];
	{
		var reader = new FileReader();
		var name = f.name;
		reader.onload = function(e) {
			if(typeof console !== 'undefined') 
                console.log("onload", new Date());
			var data = e.target.result;
				xw(data, process_wb);
		};
        reader.readAsArrayBuffer(f);
	}
}

function checkExcel() {
    drop = document.getElementById('drop');
    if(drop.addEventListener) {
        drop.addEventListener('dragenter', handleDragover, false);
        drop.addEventListener('dragover', handleDragover, false);
        drop.addEventListener('drop', handleDrop, false);
    }


    xlf = document.getElementById('xlf');
    if(xlf.addEventListener) 
        xlf.addEventListener('change', handleFile, false);
}    