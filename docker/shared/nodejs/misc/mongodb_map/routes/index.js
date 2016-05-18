var express = require('express');
var router = express.Router();

// Mongoose import
var mongoose = require('mongoose')

mongoose.connect('mongodb://mongodb:27017/kschool', function (error) {
	if (error) {
		console.log(error);
	}
});

var Schema = mongoose.Schema;

var propertiesSC = new Schema({
    title: String,
    'marker-color' : String
})

var geopositionSC = new Schema({
    type : String,
    properties : propertiesSC

})

var peopleSchema = new Schema({
	name: String,
	geoposition: geopositionSC

});

var Person = mongoose.model('JString', peopleSchema, 'test');

router.get('/people/:name', function (req, res) {
    if (req.params.name) {
        Person.findOne(
        { "name": req.params.name },
        function (err, docs) {
                res.json(docs.geoposition);
        });
    }
});

router.get('/people', function (req, res) {
    Person.find({},{'name': 1}, function (err, docs) {
        res.json(docs);
    });
});

router.get('/map', function(req,res) {
    var db = req.db;
    Person.find({},{}, function(e,docs){
        res.render('map', {
            "jmap" : docs,
            lat : 40.45626490683516,
            lng : -3.686188765503158
        });
    });
});


/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

module.exports = router;
