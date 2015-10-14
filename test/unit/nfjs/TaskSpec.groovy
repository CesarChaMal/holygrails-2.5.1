package nfjs

import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

@TestFor(Task)
class TaskSpec extends Specification {
    Task t = new Task(name: 'name', quest: new Quest(name: 'name'))

    void 'default duration is 1'() {
        expect: t.duration == 1
    }

    void 'check the duration calculation'() {
        when: 'end date is 1 beyond start date'
        t.endDate = t.startDate + 1

        then: 'the duration is 2'
        2 == t.duration
    }

    void 'default task is valid'() {
        expect: t.validate()
    }

    void 'blank name is not valid'() {
        when:
        t.name = ' '

        then:
        !t.validate()
        'blank' == t.errors['name'].code
    }

    void 'priorities below MIN are not valid'() {
        when:
        t.priority = Task.MIN_PRIORITY - 1

        then:
        !t.validate()
        'range.toosmall' == t.errors['priority'].code
    }

    void 'priorities above MAX are not valid'() {
        when:
        t.priority = Task.MAX_PRIORITY + 1

        then:
        !t.validate()
        'range.toobig' == t.errors['priority'].code
    }

    @Unroll
    void 'task with priority #p is valid'() {
        when:
        t.priority = p

        then:
        t.validate()

        where:
        p << (Task.MIN_PRIORITY..Task.MAX_PRIORITY)
    }

    void 'endDate before startDate fails'() {
        when:
        t.endDate = t.startDate - 1

        then:
        !t.validate()
        'validator.invalid' == t.errors['endDate'].code
    }
}
