//db.tweets_tlr.find().forEach(function(x){ x.timestamp_ms = new NumberLong(x.timestamp_ms); db.tweets_tlr.save(x)})

var begin = 1428451200000;
var inc = 86400000;
for(var count = 0; count < 7; count++){

var timestamp = {};
timestamp.$gte = begin;
timestamp.$lt = begin + inc;
//print(JSON.stringify(timestamp))
var positive = 'Positive';
var negative = 'Negative';
var netural = 'Neutral';

//var cp = db.tweets_tlr.find({timestamp_ms:timestamp}).count();
//print(cp)

//var cp = db.tweets_tlr.find({timestamp_ms:timestamp, sentiment:positive}).count();
//print('Positive')
//print(cp)

//var cn = db.tweets_tlr.find({timestamp_ms:timestamp, sentiment:negative}).count();
//print('Negative')
//print(cn)


var cne = db.tweets_tlr.find({timestamp_ms:timestamp, sentiment:netural}).count();
//print('Neutral')
print(cne)

begin += inc;

}
