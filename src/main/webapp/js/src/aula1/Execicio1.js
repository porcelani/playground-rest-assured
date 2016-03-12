function Execicio1() {
}
Execicio1.prototype.exec = function () {
    var resut=[];

    for (var i = 0; i <= 10; i++) {
        if (unPared(i)) {
            resut.push(i);
        }
    }

    return resut;
};

function unPared(i) {
    return i % 2;
}
