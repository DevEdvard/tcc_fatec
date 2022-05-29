package br.com.tcc.recycler.testeRecycler

class TesteDataSource {

    companion object {
        fun createDataSet(): ArrayList<TesteObjectRecycler> {

            var list = ArrayList<TesteObjectRecycler>()

            list.add(
                TesteObjectRecycler(1,
                    "Danone 120ml",
                    "Vigor",
                    1.20,
                    3.0,
                    0)
            )
            list.add(
            TesteObjectRecycler(2,
                "Danone 250ml",
                "Nesle",
                2.20,
                5.0,
                0))
            list.add(
            TesteObjectRecycler(3,
                "Requeijão 150ml",
                "Vigor",
                2.50,
                6.0,
                0))
            list.add(
                TesteObjectRecycler(1,
                    "Danone 120ml",
                    "Vigor",
                    1.20,
                    3.0,
                    0)
            )
            list.add(
            TesteObjectRecycler(2,
                "Danone 250ml",
                "Nesle",
                2.20,
                5.0,
                0))
            list.add(
            TesteObjectRecycler(3,
                "Requeijão 150ml",
                "Vigor",
                2.50,
                6.0,
                0))
            list.add(
                TesteObjectRecycler(1,
                    "Danone 120ml",
                    "Vigor",
                    1.20,
                    3.0,
                    0)
            )
            list.add(
            TesteObjectRecycler(2,
                "Danone 250ml",
                "Nesle",
                2.20,
                5.0,
                0))
            list.add(
            TesteObjectRecycler(3,
                "Requeijão 150ml",
                "Vigor",
                2.50,
                6.0,
                0))

            return list
        }
    }
}

