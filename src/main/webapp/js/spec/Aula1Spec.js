describe("Aula1", function() {
  var exe1;
  var exe2;

  beforeEach(function() {
   exe1 = new Execicio1();
   exe2 = new Execicio2();
  });

  it("1.Escreva um c�digo que mostre os n�meros �mpares entre 1 e 10.", function() {
    expect(exe1.exec()).toEqual([1,3,5,7,9]);
  });

  it("2.Mostre a m�dia do aluno ap�s ele entrar com as notas dos quatro bimestres do ano.", function() {
    expect(exe2.exec(2,3,4,5)).toEqual(3.5);
  });

});
