package com.ay.flats

import com.ay.flats.domain.Flat
import com.ay.flats.service.OldDesignOlxService
import com.ay.flats.service.OlxService
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import spock.lang.Specification

class OlxServiceTest extends Specification {

    private OlxService olxService = new OldDesignOlxService()

    def "should extract field details from HTML"() {
        given:
        int givenFloor = 19
        int givenFloorsTotal = 25
        int givenTotalSquare = 45
        int givenKitchenSquare = 5
        int givenRoomCount = 2
        and:
        // language=HTML
        def html = """
                   <table>
                    <tbody>
                     <tr> 
                      <td width="50%" class="col"> 
                       <table class="item" cellpadding="0" cellspacing="0"> 
                        <tbody>
                         <tr> 
                          <th>Оголошення від</th> 
                          <td class="value"> <strong> <a href="https://www.olx.ua/uk/nedvizhimost/kvartiry-komnaty/prodazha-kvartir-komnat/kiev/?search%5Bprivate_business%5D=business" title="Бізнес - Київ"> Бізнес </a> </strong> </td> 
                         </tr> 
                        </tbody>
                       </table> </td> 
                      <td width="50%" class="col"> 
                       <table class="item" cellpadding="0" cellspacing="0"> 
                        <tbody>
                         <tr> 
                          <th>Виберіть рубрику</th> 
                          <td class="value"> <strong> <a href="https://www.olx.ua/uk/nedvizhimost/kvartiry-komnaty/prodazha-kvartir-komnat/kiev/" title="Продаж квартир, кімнат - Київ"> Продаж квартир, кімнат </a> </strong> </td> 
                         </tr> 
                        </tbody>
                       </table> </td> 
                     </tr> 
                     <tr> 
                      <td width="50%" class="col"> 
                       <table class="item" cellpadding="0" cellspacing="0"> 
                        <tbody>
                         <tr> 
                          <th>Тип об'єкта</th> 
                          <td class="value"> <strong> <a href="https://www.olx.ua/nedvizhimost/kvartiry-komnaty/prodazha-kvartir-komnat/kvartira/kiev/" title="Квартира - Київ"> Квартира </a> </strong> </td> 
                         </tr> 
                        </tbody>
                       </table> 
                      </td> 
                      <td width="50%" class="col"> 
                       <table class="item" cellpadding="0" cellspacing="0"> 
                        <tbody>
                         <tr> 
                          <th>Поверх</th> 
                          <td class="value"> <strong> $givenFloor </strong> </td> 
                         </tr> 
                        </tbody>
                       </table> 
                      </td> 
                     </tr> 
                     <tr> 
                      <td width="50%" class="col"> 
                       <table class="item" cellpadding="0" cellspacing="0"> 
                        <tbody>
                         <tr> 
                          <th>Поверховість</th> 
                          <td class="value"> <strong> $givenFloorsTotal </strong> </td> 
                         </tr> 
                        </tbody>
                       </table> </td> 
                      <td width="50%" class="col"> 
                       <table class="item" cellpadding="0" cellspacing="0"> 
                        <tbody>
                         <tr> 
                          <th>Загальна площа</th> 
                          <td class="value"> <strong> $givenTotalSquare м² </strong> </td> 
                         </tr> 
                        </tbody>
                       </table> </td> 
                     </tr> 
                     <tr> 
                      <td width="50%" class="col"> 
                       <table class="item" cellpadding="0" cellspacing="0"> 
                        <tbody>
                         <tr> 
                          <th>Площа кухні</th> 
                          <td class="value"> <strong> $givenKitchenSquare м² </strong> </td> 
                         </tr> 
                        </tbody>
                       </table> </td> 
                      <td width="50%" class="col"> 
                       <table class="item" cellpadding="0" cellspacing="0"> 
                        <tbody>
                         <tr> 
                          <th>Кількість кімнат</th> 
                          <td class="value"> <strong> $givenRoomCount </strong> </td> 
                         </tr> 
                        </tbody>
                       </table> </td> 
                     </tr> 
                    </tbody>
                   </table>
                   """

        when:
        Element tbody = Jsoup.parse(html).body().selectFirst("table > tbody")
        Flat flat = olxService.extractDetailedFlatData(tbody)
        then:
        flat.floor == givenFloor
        flat.floorsTotal == givenFloorsTotal
        flat.totalSquare == givenTotalSquare
        flat.kitchenSquare == givenKitchenSquare
        flat.roomCount == givenRoomCount
    }
}
