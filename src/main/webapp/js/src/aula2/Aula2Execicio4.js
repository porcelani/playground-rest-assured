function Aula2Execicio4() {
}
Aula2Execicio4.prototype.exec = function (array) {
    return array.map(inDolar);
};

function inDolar(reais){
    const COTACAO = 4.17;
    return reais*COTACAO;

}
