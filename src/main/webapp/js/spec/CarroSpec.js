describe("Carro", function() {
    var carro;

    beforeEach(function() {
        carro = new Carro();
    });

    it("should be able start Carro", function() {
        expect(carro.ligar()).toEqual("Carro Ligado");
    });

});
