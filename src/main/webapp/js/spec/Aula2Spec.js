describe("Aula2 - Para var valores=[1, 0.5, 2.5, 20.35, 0.99, 'a', 1.99, 5, 'b'];", function() {

  var valores=[1, 0.5, 2.5, 20.35, 0.99, 'a', 1.99, 5, 'b'];
  var exe1;
  var exe4;

  beforeEach(function() {
   exe1 = new Aula2Execicio1();
   exe4 = new Aula2Execicio4();
  });


  it("1.Verifique se todos os elementos do array são números.", function() {
    expect(exe1.isNumber(1)).toEqual(true);
    expect(exe1.isNumber(0.5)).toEqual(true);
    expect(exe1.isNumber('a')).toEqual(false);

    //expect(exe1.everyElementsIsNumbers(valores)).toEqual(false);

  });


  it("4.Atualize os valores dos elementos deste array convertendo-os para valores em reais, assumindo que os valores no array original estão em dólares; js var USDtoBRL = 4.17; // cotação do dólar", function () {
    var array = [1, 0.5, 2.5, 20.35, 0.99, 1.99, 5, 10, 6];
    expect(exe4.exec(array)).toEqual([4.17, 2.085, 10.425, 84.85950000000001, 4.1283, 8.2983, 20.85, 41.7, 25.02 ]);
  });

});
